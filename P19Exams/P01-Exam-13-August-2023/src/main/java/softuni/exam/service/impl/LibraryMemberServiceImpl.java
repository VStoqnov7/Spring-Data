package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.LibraryMemberImportDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private final String STARS_PATH = "src/main/resources/files/json/library-members.json";

    private LibraryMemberRepository libraryMemberRepository;
    private MyValidator validation;

    private ModelMapper modelMapper;

    private Gson gson;

    @Autowired
    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, MyValidator validation, ModelMapper modelMapper, Gson gson) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.validation = validation;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files
                .readString(Path.of(STARS_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder sb = new StringBuilder();

        LibraryMemberImportDTO[] librarySeedDTO = gson.fromJson(readLibraryMembersFileContent(),LibraryMemberImportDTO[].class);

        Arrays.stream(librarySeedDTO)
                .filter(libraryMemberImportDTO -> {
                    boolean isValid = validation.isValid(libraryMemberImportDTO);


                    Optional<LibraryMember> bookByTitle = libraryMemberRepository.findByPhoneNumber(libraryMemberImportDTO.getPhoneNumber());
                    if (bookByTitle.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ?
                            String.format("Successfully imported library member %s - %s",libraryMemberImportDTO.getFirstName(),libraryMemberImportDTO.getLastName())
                            : "Invalid library member"
                            ).append(System.lineSeparator());
                    return isValid;
                })
                .map(libraryMemberImportDTO -> {

                    LibraryMember map = modelMapper.map(libraryMemberImportDTO, LibraryMember.class);
                    return map;
                })
                .forEach(libraryMemberRepository::save);

        return sb.toString().trim();
    }
}
