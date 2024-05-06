package exam.service.impl;

import exam.model.dto.TownImportContainerDTO;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.MyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class TownServiceImpl implements TownService {

    private final String TOWN_PATH = "src/main/resources/files/xml/towns.xml";

    private final ModelMapper modelMapper;
    private final MyValidator validator;

    private final TownRepository townRepository;

    public TownServiceImpl(ModelMapper modelMapper, MyValidator validator, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
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
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(TownImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        TownImportContainerDTO townDTO = (TownImportContainerDTO) unmarshaller.unmarshal(new File(TOWN_PATH));

        townDTO.getTowns()
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Town> byName = this.townRepository.findByName(dto.getName());

                    if (isValid && !byName.isPresent()){
                        Town town = modelMapper.map(dto,Town.class);
                        townRepository.save(town);
                        sb.append(String.format("Successfully imported Town %s",dto.getName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Town").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
