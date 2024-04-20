package gamestore.services;

import gamestore.entities.Game;
import gamestore.entities.Order;
import gamestore.entities.User;
import gamestore.entities.models.UserRegisterDto;
import gamestore.repositories.GameRepository;
import gamestore.repositories.OrderRepository;
import gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gamestore.enums.ErrorMessages.EMAIL_ALREADY_EXISTS;

@Service
public class UserServiceImpl implements UserService {

    private List<User> loggedInUsers;
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = new ModelMapper();
        this.loggedInUsers = new ArrayList<>();
    }

    @Override
    public void registerUser(String[] data) {
        final String email = data[1];
        final String password = data[2];
        final String confirmPassword = data[3];
        final String fullName = data[4];

        UserRegisterDto userRegisterDto = null;

        try {
            userRegisterDto = new UserRegisterDto(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        if (this.userRepository.findByEmailAndPassword(userRegisterDto.getEmail(), userRegisterDto.getPassword()).isPresent()) {
            System.out.println(EMAIL_ALREADY_EXISTS);
            return;
        }

        final User user = this.modelMapper.map(userRegisterDto, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }

        this.userRepository.saveAndFlush(user);

        System.out.println(fullName + " was registered.");
    }

    @Override
    public void loginUser(String[] data) {
        final String email = data[1];
        final String password = data[2];

        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (loggedInUsers.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getFullName().equals(user.getFullName()))) {
                System.out.println(user.getFullName() + " is already logged in.");
            } else {
                loggedInUsers.add(user);
                System.out.println("Successfully logged in " + user.getFullName());
            }
        } else {
            System.out.println("Incorrect username or password.");
        }

    }

    @Override
    public void logoutUser(String[] data) {
        final String email = data[1];
        final String password = data[2];

        boolean isUserLoggedIn = loggedInUsers.stream()
                .anyMatch(u -> u.getEmail().equals(email) && u.getPassword().equals(password));

        if (!isUserLoggedIn) {
            System.out.println("Cannot log out. This user wasn't logged in.");
            return;
        }

        User userToRemove = loggedInUsers.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        loggedInUsers.remove(userToRemove);
        System.out.println("Successfully logged out " + userToRemove.getFullName());
    }

    @Override
    public void buyGame(String[] data) {
        boolean isUserLoggedIn = loggedInUsers.stream()
                .anyMatch(u -> u.getEmail().equals(data[1]));
        if (!isUserLoggedIn) {
            System.out.println("User with email '" + data[1] + "' not found.");
            return;
        }
        String userEmail = data[1];
        String gameTitle = data[2];
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        Optional<Game> optionalGame = gameRepository.findFirstByTitle(gameTitle);
        if (optionalUser.isPresent() && optionalGame.isPresent()) {
            User user = optionalUser.get();
            Game game = optionalGame.get();
            user.getGames().add(game);
            userRepository.save(user);
            System.out.println("Game '" + gameTitle + "' purchased successfully by user '" + user.getFullName() + "'.");
        } else {
            System.out.println("Failed to purchase game. Game not found.");
        }
    }

    @Override
    public void ownerGames(String[] data) {
        if (extracted(data)) return;
        String userEmail = data[1];
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getGames().isEmpty()){
                System.out.println("You have no games available");
            }
            user.getGames().forEach(game -> System.out.println(game.getTitle()));
        }
    }

    private boolean extracted(String[] data) {
        boolean isUserLoggedIn = loggedInUsers.stream()
                .anyMatch(u -> u.getEmail().equals(data[1]));
        if (!isUserLoggedIn) {
            System.out.println("You are not logged in.");
            return true;
        }
        return false;
    }

    @Override
    public void addItem(String[] data) {
        if (extracted(data)) return;
        String userEmail = data[1];
        String gameTitle = data[2];
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user = optionalUser.get();
        if (user.getShoppingCart().stream().anyMatch(game -> game.getTitle().equals(gameTitle))) {
            System.out.println("You already own this game in the cart.");
            return;
        }

        Optional<Game> optionalGame = gameRepository.findFirstByTitle(gameTitle);
        if (optionalGame.isEmpty()) {
            System.out.println("No such game.");
            return;
        }
        Game game = optionalGame.get();
        user.getShoppingCart().add(game);
        userRepository.save(user);
        System.out.println("You add this game to the cart.");
    }

    @Override
    public void removeItem(String[] data) {
        if (extracted(data)) return;
        String userEmail = data[1];
        String gameTitle = data[2];
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            System.out.println("User not found.");
            return;
        }
        User user = optionalUser.get();
        Game gameToRemove = user.getShoppingCart().stream().filter(game -> game.getTitle().equals(gameTitle)).findFirst().orElse(null);

        if (gameToRemove == null) {
            System.out.println("You don't have this game in the cart.");
            return;
        }

        user.getShoppingCart().remove(gameToRemove);
        userRepository.save(user);
        System.out.println("The game has been successfully removed from the cart.");
    }

    @Override
    public void buyAllItemsInTheCard(String[] data) {
        if (extracted(data)) return;

        String userEmail = data[1];

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        User user = optionalUser.get();

        if (user.getShoppingCart().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        Order order = new Order();
        order.setUser(user);
        order.setGames(user.getShoppingCart());
        orderRepository.save(order);

        for (Game game : user.getShoppingCart()) {
            user.getGames().add(game);
        }

        user.getShoppingCart().clear();
        userRepository.save(user);

        System.out.println("Games have been purchased successfully.");
    }

    @Override
    public boolean isLoggedUserAdmin(String email) {
        return loggedInUsers.stream()
                .anyMatch(u -> u.getEmail().equals(email) && u.getAdmin());

    }
}
