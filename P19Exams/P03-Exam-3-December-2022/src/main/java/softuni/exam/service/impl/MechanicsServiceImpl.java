package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static String MECHANICS_JSON_PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicsRepository mechanicRepository;
    private final Gson gson;
    private final MyValidator validator;

    private final ModelMapper modelMapper;

    @Autowired
    public MechanicsServiceImpl(MechanicsRepository mechanicRepository, Gson gson, MyValidator validator, ModelMapper modelMapper) {
        this.mechanicRepository = mechanicRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_JSON_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();

        MechanicImportDTO[] mechanicDTO = gson.fromJson(readMechanicsFromFile(),MechanicImportDTO[].class);
        Arrays.stream(mechanicDTO)
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Mechanic> mechanicByEmail = this.mechanicRepository.findByEmail(dto.getEmail());
                    if (mechanicByEmail.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported mechanic %s %s",
                                dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid mechanic").append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(dto-> modelMapper.map(dto,Mechanic.class))
                .forEach(dto-> this.mechanicRepository.save(dto));

        return sb.toString().trim();
    }
}
