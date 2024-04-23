package com.example.modelmapper_json_xml.services;

import com.example.modelmapper_json_xml.entiti.user.User;
import com.example.modelmapper_json_xml.entiti.user.UserJSONImportDTO;
import com.example.modelmapper_json_xml.entiti.user.UsersXMLDTO;
import com.example.modelmapper_json_xml.repositories.UserRepository;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.modelmapper_json_xml.enums.ReadJsonPaths.USERS_JSON_PATH;
import static com.example.modelmapper_json_xml.enums.ReadJsonPaths.USERS_XML_PATH;

@Service
public class SeedService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SeedService(UserRepository userRepository, /*@Qualifier("име на been ")*/ ModelMapper modelMapper, /*@Qualifier("име на been ")*/ Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;         // Implements in Configuration !
        this.gson = gson;                       // Implements in Configuration !
    }

    public void seedUsersJSON() throws FileNotFoundException {
        if (userRepository.count() == 0) {
            FileReader reedUsers = new FileReader(USERS_JSON_PATH);
            UserJSONImportDTO[] userImportDTOS = this.gson.fromJson(reedUsers, UserJSONImportDTO[].class);

            List<User> users = Arrays.stream(userImportDTOS)
                    .map(dto -> this.modelMapper.map(dto, User.class))
                    .collect(Collectors.toList());

            this.userRepository.saveAllAndFlush(users);
        }
    }

    public void seedUsersXML() throws JAXBException, FileNotFoundException {
        if (userRepository.count() == 0) {
            JAXBContext context = JAXBContext.newInstance(UsersXMLDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try (FileReader fileReader = new FileReader(USERS_XML_PATH)) {
                UsersXMLDTO usersContainerDTO = (UsersXMLDTO) unmarshaller.unmarshal(fileReader);

                List<User> users = usersContainerDTO.getUsers().stream()
                        .map(dto -> modelMapper.map(dto, User.class))
                        .collect(Collectors.toList());

                userRepository.saveAllAndFlush(users);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

