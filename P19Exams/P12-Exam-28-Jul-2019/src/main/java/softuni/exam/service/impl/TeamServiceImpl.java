package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.dto.TeamsImportContainerDTO;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.TeamService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final String TEAM_PATH = "src/main/resources/files/xml/teams.xml";
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;

    public TeamServiceImpl(ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil, TeamRepository teamRepository, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public String importTeams() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(TeamsImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TeamsImportContainerDTO teamDTO = (TeamsImportContainerDTO) unmarshaller.unmarshal(new File(TEAM_PATH));

        teamDTO.getTeams()
                .forEach(dto-> {
                    boolean isValid = validatorUtil.isValid(dto);
                    Optional<Picture> byUrl = this.pictureRepository.findByUrl(dto.getPicture().getUrl());
                    if (isValid && byUrl.isPresent()){
                        Team team = modelMapper.map(dto,Team.class);
                        team.setPicture(byUrl.get());
                        this.teamRepository.save(team);
                        sb.append(String.format("Successfully imported - %s",dto.getName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid team").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return fileUtil.readFile(TEAM_PATH);
    }
}
