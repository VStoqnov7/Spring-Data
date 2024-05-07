package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.Posts;
import softuni.exam.instagraphlite.models.Users;
import softuni.exam.instagraphlite.models.dto.PostImportContainerDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final String POST_PATH = "src/main/resources/files/posts.xml";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    public PostServiceImpl(ModelMapper modelMapper, MyValidator validator, PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POST_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(PostImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PostImportContainerDTO postDTO = (PostImportContainerDTO) unmarshaller.unmarshal(new File(POST_PATH));
        postDTO.getPosts()
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Pictures> picture = this.pictureRepository.findByPath(dto.getPicture().getPath());
                    Optional<Users> user = this.userRepository.findByUsername(dto.getUser().getUsername());
                    if (isValid && picture.isPresent() && user.isPresent()){
                        Posts posts = modelMapper.map(dto,Posts.class);
                        posts.setUser(user.get());
                        posts.setPicture(picture.get());
                        postRepository.save(posts);
                        sb.append(String.format("Successfully imported Post, made by %s",dto.getUser().getUsername())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Post").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
