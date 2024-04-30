package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationImportDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final MyValidator validator;

    private final ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, MyValidator validator, ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        String CONSTELLATION_JSON_PATH = "src/main/resources/files/json/constellations.json";
        return Files.readString(Path.of(CONSTELLATION_JSON_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();
        ConstellationImportDTO[] constellationDTO = gson.fromJson(readConstellationsFromFile(),ConstellationImportDTO[].class);

        Arrays.stream(constellationDTO)
                .filter(dto -> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Constellation> byName = this.constellationRepository.findByName(dto.getName());
                    if (byName.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported constellation %s - %s",
                                dto.getName(),dto.getDescription())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid constellation").append(System.lineSeparator());
                    }

                    return isValid;
                })
                .map(dto -> modelMapper.map(dto,Constellation.class))
                .forEach(this.constellationRepository::save);

        return sb.toString().trim();
    }
}
