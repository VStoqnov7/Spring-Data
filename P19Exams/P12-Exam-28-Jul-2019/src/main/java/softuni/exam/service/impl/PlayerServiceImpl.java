package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.dto.PlayerExportBiggerThanDTO;
import softuni.exam.domain.entities.dto.PlayerExportDTO;
import softuni.exam.domain.entities.dto.PlayerImportDTO;
import softuni.exam.domain.entities.dto.TeamNameAndPlayerExportDTO;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.PlayerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final String PLAYER_PATH = "src/main/resources/files/json/players.json";
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final PlayerRepository playerRepository;
    private final PictureRepository pictureRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil, Gson gson, PlayerRepository playerRepository, PictureRepository pictureRepository, TeamRepository teamRepository) {
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.playerRepository = playerRepository;
        this.pictureRepository = pictureRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();

        String playerJson = fileUtil.readFile(PLAYER_PATH);
        PlayerImportDTO[] playerDTO = gson.fromJson(playerJson, PlayerImportDTO[].class);
        Arrays.stream(playerDTO)
                .forEach(dto->{
                    boolean isValid = validatorUtil.isValid(dto);
                    Optional<Picture> picture = this.pictureRepository.findByUrl(dto.getPicture().getUrl());
                    Optional<Team> team = this.teamRepository.findByName(dto.getTeam().getName());
                    if (isValid && picture.isPresent() && team.isPresent()){
                        Player player = modelMapper.map(dto, Player.class);
                        player.setPicture(picture.get());
                        player.setTeam(team.get());
                        this.playerRepository.save(player);
                        sb.append(String.format("Successfully imported player: %s %s",dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid player").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile(PLAYER_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        List<PlayerExportBiggerThanDTO> players = this.playerRepository.findByFirstLastNameNumberSalaryTeamName();
        players.forEach(dto-> {
            sb.append(String.format("Player name: %s %s",dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
            sb.append(String.format("Number: %d",dto.getNumber())).append(System.lineSeparator());
            sb.append(String.format("Salary: %.2f",dto.getSalary())).append(System.lineSeparator());
            sb.append(String.format("Team: %s",dto.getTeamName())).append(System.lineSeparator());
        });


        return sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        Optional<TeamNameAndPlayerExportDTO> team = this.teamRepository.findByNameNorthHub();
        List<PlayerExportDTO> players = this.playerRepository.findByTeamName();
        team.get().setPlayers(players);

        sb.append(String.format("Team: %s",team.get().getTeamName())).append(System.lineSeparator());
        players.forEach(player -> {
            sb.append(String.format("Player name: %s %s - %s",player.getFirstName(),player.getLastName(),player.getPosition())).append(System.lineSeparator());
            sb.append(String.format("Number: %d",player.getNumber())).append(System.lineSeparator());
        });
        return sb.toString().trim();
    }
}
