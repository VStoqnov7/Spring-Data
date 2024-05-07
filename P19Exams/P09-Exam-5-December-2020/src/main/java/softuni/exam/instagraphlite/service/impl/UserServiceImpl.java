package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.Users;
import softuni.exam.instagraphlite.models.dto.PostDetailDTO;
import softuni.exam.instagraphlite.models.dto.UserExportDTO;
import softuni.exam.instagraphlite.models.dto.UsersImportDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final String USER_PATH = "src/main/resources/files/users.json";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final Gson gson;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    private final PostRepository postRepository;

    public UserServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, UserRepository userRepository, PictureRepository pictureRepository, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.postRepository = postRepository;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USER_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();

        UsersImportDTO[] userDTO = this.gson.fromJson(readFromFileContent(), UsersImportDTO[].class);
        Arrays.stream(userDTO)
                .forEach(dto -> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Users> byUsername = this.userRepository.findByUsername(dto.getUsername());
                    Optional<Pictures> pictureByPath = this.pictureRepository.findByPath(dto.getProfilePicture());
                    if (isValid && !byUsername.isPresent() && pictureByPath.isPresent()) {
                        Users users = modelMapper.map(dto, Users.class);
                        users.setPicture(pictureByPath.get());
                        this.userRepository.save(users);
                        sb.append(String.format("Successfully imported User: %s", dto.getUsername())).append(System.lineSeparator());
                    } else {
                        sb.append("Invalid User").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder sb = new StringBuilder();
        List<UserExportDTO> users = this.userRepository.findUsersByUsernameCountPosts();
        for (UserExportDTO user : users) {
            List<PostDetailDTO> posts = postRepository.findPostsByUser(user.getUsername());
            user.setPostDetails(posts);
        }

        users.forEach(dto -> {
            sb.append(String.format("User: %s", dto.getUsername())).append(System.lineSeparator());
            sb.append(String.format("Post count: %d", dto.getPostDetails().size())).append(System.lineSeparator());
            sb.append("==Post Details:").append(System.lineSeparator());
            dto.getPostDetails().forEach(postDetail -> {
                sb.append(String.format("----Caption: %s", postDetail.getCaption())).append(System.lineSeparator());
                sb.append(String.format("----Picture Size: %.2f", postDetail.getPictureSize())).append(System.lineSeparator());
            });
        });
        return sb.toString().trim();
    }
}
