package gamestore.services;

public interface UserService {

    public boolean isLoggedUserAdmin(String email);
    void registerUser(String[] data);

    void loginUser(String[] data);

    void logoutUser(String[] data);

    void buyGame(String[] data);

    void ownerGames(String[] data);

    void addItem(String[] data);

    void removeItem(String[] data);

    void buyAllItemsInTheCard(String[] data);

}
