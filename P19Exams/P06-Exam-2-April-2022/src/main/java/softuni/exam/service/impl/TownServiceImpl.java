package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private final String TOWN_PATH = "src/main/resources/files/json/towns.json";

    private final ModelMapper modelMapper;

    private final MyValidator validator;

    private final Gson gson;

    private final TownRepository townRepository;

    public TownServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.townRepository = townRepository;
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

        TownImportDTO[] townDTO = gson.fromJson(readTownsFileContent(), TownImportDTO[].class);

        Arrays.stream(townDTO)
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Town> byTownName = this.townRepository.findByTownName(dto.getTownName());
                    if (isValid && !byTownName.isPresent()){
                        Town town = modelMapper.map(dto,Town.class);
                        this.townRepository.save(town);
                        sb.append(String.format("Successfully imported town %s - %d",dto.getTownName(),dto.getPopulation())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid town").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
