package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Town;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtilImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private final String TOWN_PATH = "src/main/resources/files/json/towns.json";
    private final ModelMapper modelMapper;
    private final ValidationUtilImpl validationUtil;
    private final TownRepository townRepository;
    private final Gson gson;

    public TownServiceImpl(ModelMapper modelMapper, ValidationUtilImpl validationUtil, TownRepository townRepository, Gson gson) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        TownImportDTO[] townDTO = gson.fromJson(readTownsFileContent(),TownImportDTO[].class);

        Arrays.stream(townDTO)
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Town> byName = this.townRepository.findByName(dto.getName());

                    if (isValid && !byName.isPresent()){
                        Town town = modelMapper.map(dto,Town.class);
                        this.townRepository.save(town);
                        sb.append(String.format("Successfully imported Town %s - %d",dto.getName(),dto.getPopulation())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Town").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
