package com.example.football.service.impl;

import com.example.football.models.dto.PlayerExportDTO;
import com.example.football.models.dto.PlayerImportContainerDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
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
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final String PLAYER_PATH = "src/main/resources/files/xml/players.xml";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;

    public PlayerServiceImpl(ModelMapper modelMapper, MyValidator validator, PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(PlayerImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PlayerImportContainerDTO playerDTO = (PlayerImportContainerDTO) unmarshaller.unmarshal(new File(PLAYER_PATH));
        playerDTO.getPlayers()
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Player> byEmail = this.playerRepository.findByEmail(dto.getEmail());
                    if (isValid && !byEmail.isPresent()){
                        Player player = modelMapper.map(dto,Player.class);
                        Optional<Town> town = this.townRepository.findByName(dto.getTown().getName());
                        Optional<Team> team = this.teamRepository.findByName(dto.getTeam().getName());
                        Optional<Stat> stat = this.statRepository.findById(dto.getStat().getId());
                        player.setTown(town.get());
                        player.setTeam(team.get());
                        player.setStat(stat.get());
                        this.playerRepository.save(player);
                        sb.append(String.format("Successfully imported Player %s %s - %s",
                                dto.getFirstName(),dto.getLastName(),dto.getPosition())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Player").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();
        List<PlayerExportDTO> player = this.playerRepository.findPlayerByFirstNameLAndLastNameAndPositionTeamNameTeamStadiumName();
        player.forEach(dto-> {
            sb.append(String.format("Player - %s %s",dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
            sb.append(String.format("   Position - %s",dto.getPosition())).append(System.lineSeparator());
            sb.append(String.format("   Team - %s",dto.getTeamName())).append(System.lineSeparator());
            sb.append(String.format("   Stadium - %s",dto.getTeamStadiumName())).append(System.lineSeparator());
        });
        return sb.toString().trim();
    }
}
