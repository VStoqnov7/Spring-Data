package gamestore.services;

import gamestore.entities.Game;
import gamestore.entities.models.GameAddDto;
import gamestore.entities.models.GameEditDto;
import gamestore.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService{

    private final ModelMapper modelMapper;

    private final GameRepository gameRepository;
    private final UserService userService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public void addGame(String[] data) {
        if (!this.userService.isLoggedUserAdmin(data[1])){
            System.out.println("Logged user is not admin.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final String title = data[2];
        final BigDecimal price = new BigDecimal(data[3]);
        final BigDecimal size = new BigDecimal(data[4]);
        final String trailerId = data[5] ;
        final String thumbnailURL = data[6];
        final String description = data[7];
        final LocalDate releaseDate = LocalDate.parse(data[8], formatter);

        final GameAddDto gameDto = new GameAddDto(title,
                price,
                size,
                trailerId,
                thumbnailURL,
                description,
                releaseDate);

        Game gameToBeSaved = this.modelMapper.map(gameDto, Game.class);

        this.gameRepository.saveAndFlush(gameToBeSaved);

        System.out.println("Added " + gameDto.getTitle());
    }

    @Override
    public void editGame(String[] data) {
        if (!this.userService.isLoggedUserAdmin(data[1])){
            System.out.println("Logged user is not admin.");
            return;
        }

        final Optional<Game> gameToBeEdited = this.gameRepository.findById(Long.valueOf(data[2]));

        if (gameToBeEdited.isEmpty()){
            System.out.println("No Such game");
            return;
        }

        Map<String, String> updatingArguments = new HashMap<>();

        for (int i = 3; i < data.length; i++) {
            String[] argumentsForUpdate = data[i].split("=");
            updatingArguments.put(argumentsForUpdate[0], argumentsForUpdate[1]);
        }

        final GameEditDto gameEditDto = this.modelMapper.map(gameToBeEdited.get(), GameEditDto.class);

        gameEditDto.updateFields(updatingArguments);

        Game gameToBeSaved = this.modelMapper.map(gameEditDto, Game.class);

        this.gameRepository.saveAndFlush(gameToBeSaved);

        System.out.println("Edited " + gameEditDto.getTitle());

    }

    @Override
    public void deleteGame(String[] data) {
        if (!this.userService.isLoggedUserAdmin(data[1])){
            System.out.println("Logged user is not admin.");
            return;
        }
        Optional<Game> gameToBeDeleted  = this.gameRepository.findById(Long.parseLong(data[2]));

        if (gameToBeDeleted.isEmpty()){
            System.out.println("No Such game");
            return;
        }

        this.gameRepository.delete(gameToBeDeleted.get());

        System.out.println("Deleted " + gameToBeDeleted.get().getTitle());
    }

    @Override
    public void allGamesTitles() {
        List<Game> games = gameRepository.findAll();
        if (games.isEmpty()){
            System.out.println("No Such game");
            return;
        }
        for (Game game : games) {
            System.out.println("Title: " + game.getTitle() + ", Price: " + game.getPrice());
        }
    }

    @Override
    public void detailsGame(String[] data) {
        Optional<Game> optionalGame = gameRepository.findFirstByTitle(data[1]);
        if (optionalGame.isEmpty()){
            System.out.println("No Such game");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Game game = optionalGame.get();
        System.out.printf("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s%n",game.getTitle(),game.getPrice(),game.getDescription(),game.getReleaseDate().format(formatter));
    }
}
