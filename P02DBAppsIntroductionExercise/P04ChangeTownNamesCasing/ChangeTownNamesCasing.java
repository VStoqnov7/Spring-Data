import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    private static final String UPDATE_TOWN_NAME = "UPDATE `towns` SET `name` = UPPER(`name`) WHERE `country` = ?";
    private static final String GET_ALL_TOWN_BY_COUNTRY = "SELECT `name` FROM `towns` WHERE `country` = ?";
    private static final String NO_TOWNS_AFFECTED = "No town names were affected.";
    private static final String TOWNS_AFFECTED = "%d town names were affected.%n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getSQLConnection();
        final String country = scanner.nextLine();
        final PreparedStatement statement = connection.prepareStatement(UPDATE_TOWN_NAME);
        statement.setString(1,country);

       final int updateCount = statement.executeUpdate();

       if (updateCount == 0){
           System.out.printf(NO_TOWNS_AFFECTED);
           connection.close();
           return;
       }

       System.out.printf(TOWNS_AFFECTED,updateCount);

       final PreparedStatement selectAllTowns = connection.prepareStatement(GET_ALL_TOWN_BY_COUNTRY);
       selectAllTowns.setString(1,country);
        ResultSet allTownsResultSet = selectAllTowns.executeQuery();

        ArrayList<String> towns = new ArrayList<>();

        while (allTownsResultSet.next()){
            towns.add(allTownsResultSet.getString("name"));
        }

        System.out.println(towns);
    }
}
