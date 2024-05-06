package com.example.football.service.impl;

import com.example.football.models.dto.StatImportContainerDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.MyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {
    private final String STAT_PATH = "src/main/resources/files/xml/stats.xml";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final StatRepository statRepository;

    public StatServiceImpl(ModelMapper modelMapper, MyValidator validator, StatRepository statRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.statRepository = statRepository;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STAT_PATH));
    }

    @Override
    public String importStats() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(StatImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StatImportContainerDTO statDTO = (StatImportContainerDTO) unmarshaller.unmarshal(new File(STAT_PATH));

        statDTO.getStats()
                .forEach(dto -> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Stat> optionalStat = this.statRepository.findByPassingAndShootingAndEndurance(dto.getPassing(), dto.getShooting(), dto.getEndurance());

                    if (isValid && !optionalStat.isPresent()){
                        Stat stat = modelMapper.map(dto,Stat.class);
                        this.statRepository.save(stat);
                        sb.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f",dto.getShooting(),dto.getPassing(),dto.getEndurance())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Stat").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
