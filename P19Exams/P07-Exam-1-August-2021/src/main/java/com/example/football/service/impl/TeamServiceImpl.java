package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.MyValidator;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final String TEAM_PATH = "src/main/resources/files/json/teams.json";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final Gson gson;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;

    public TeamServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, TeamRepository teamRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAM_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();
        TeamImportDTO[] teamDTO = gson.fromJson(readTeamsFileContent(),TeamImportDTO[].class);

        Arrays.stream(teamDTO)
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Team> optionalTeam = this.teamRepository.findByName(dto.getName());
                    if (isValid && !optionalTeam.isPresent()){
                        Team team = modelMapper.map(dto,Team.class);
                        Optional<Town> optionalTown = this.townRepository.findByName(dto.getTownName());
                        team.setTown(optionalTown.get());
                        this.teamRepository.save(team);
                        sb.append(String.format("Successfully imported Team %s - %d",dto.getName(),dto.getFanBase())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Team").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
