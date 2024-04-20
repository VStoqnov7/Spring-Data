package gamestore;

import gamestore.services.GameService;
import gamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static gamestore.enums.Commands.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    private final Scanner scanner;

    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {

        String input = scanner.nextLine();

        while (!input.equals("close")) {
            String[] data = input.split("\\|");

            /**
             * RegisterUser|<email>|<password>|<confirmPassword>|<fullName> -> RegisterUser|ivan@ivan.com|Ivan12|Ivan12|Ivan
             * LoginUser|<email>|<password>  -> LoginUser|ivan@ivan.com|Ivan12
             * Logout|<email>|<password>  -> Logout|ivan@ivan.com|Ivan12
             * AddGame|<email>|<title>|<price>|<size>|<trailer>|<thumbnailURL>|<description>|<releaseDate> -> AddGame|ivan@ivan.com|Overwatch|100.00|15.5|FqnKB22pOC0|https://www.youtube.com/watch?v=edYCtaNueQY|Overwatch is a team-based multiplayer online first-person shooter video game developed and published by Blizzard Entertainment.|24-05-2016
             * EditGame|<email>|<id>|<values>|<email>  -> EditGame|ivan@ivan.com|1|price=80.00|size=12.0
             * DeleteGame|<email>|<id>  -> DeleteGame|ivan@ivan.com|1
             * AllGames
             * BuyGame|<email>|<title> -> BuyGame|ivan@ivan.com|Overwatch
             * DetailGame|<title>  -> DetailsGame|Overwatch
             * OwnedGames|<email>  -> OwnedGames|ivan@ivan.com
             * AddItem|<email>|<gameTitle>  -> AddItem|ivan@ivan.com|Overwatch
             * RemoveItem|<email>|<gameTitle> -> RemoveItem|ivan@ivan.com|Overwatch
             * BuyItem|<email> -> BuyItem|ivan@ivan.com
             */

            switch (data[0]) {
                case REGISTER_USER:
                    this.userService.registerUser(data);
                    break;
                case LOGIN_USER:
                    this.userService.loginUser(data);
                    break;
                case LOGOUT:
                    this.userService.logoutUser(data);
                    break;
                case ADD_GAME:
                    this.gameService.addGame(data);
                    break;
                case EDIT_GAME:
                    this.gameService.editGame(data);
                    break;
                case DELETE_GAME:
                    this.gameService.deleteGame(data);
                    break;
                case ALL_GAMES:
                    this.gameService.allGamesTitles();
                    break;
                case DETAILS_GAME:
                    this.gameService.detailsGame(data);
                    break;
                case BUY_GAME:
                    this.userService.buyGame(data);
                    break;
                case ALL_GAMES_CURRENT_USER:
                    this.userService.ownerGames(data);
                    break;
                case ADD_ITEM:
                    this.userService.addItem(data);
                    break;
                case REMOVE_ITEM:
                    this.userService.removeItem(data);
                    break;
                case BUY_All_ITEMS_IN_THE_CARD:
                    this.userService.buyAllItemsInTheCard(data);
                    break;
                default:
                    System.out.println("Invalid input");
            }

            input = scanner.nextLine();
        }


    }
}
