package gamestore.services;

public interface GameService {

    void addGame(String[] data);

    void editGame(String[] data);

    void deleteGame(String[] data);

    void allGamesTitles();

    void detailsGame(String[] data);


}
