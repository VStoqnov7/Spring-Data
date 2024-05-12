package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.TownImportDTO;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static hiberspring.common.Constants.*;

@Service
public class TownServiceImpl implements TownService {
    private final String TOWN_PATH = PATH_TO_FILES + "towns.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;

    public TownServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, TownRepository townRepository, FileUtil fileUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return fileUtil.readFile(TOWN_PATH);
    }

    @Override
    public String importTowns(String townsFileContent){
        StringBuilder sb = new StringBuilder();

        TownImportDTO[] townDTO = gson.fromJson(townsFileContent,TownImportDTO[].class);

        Arrays.stream(townDTO)
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    if (isValid){
                        Town town = modelMapper.map(dto,Town.class);
                        this.townRepository.save(town);
                        sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,"Town",dto.getName())).append(System.lineSeparator());
                    }else {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
