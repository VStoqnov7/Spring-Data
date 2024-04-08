import Utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseMinionsAge {

    private static final String UPDATE_MINION_NAME_AND_AGE =
            "UPDATE `minions` SET `name` = LOWER(`name`), `age` = `age` + 1 WHERE `id` IN (?)";
    private static final String MINION_NAMES_ID =
            "SELECT `name`, `age` FROM `minions`";
    private static final String PRINT_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getSQLConnection();
        String[] minionId = scanner.nextLine().split(" ");

        for (int i = 0; i < minionId.length; i++) {
            int currentMinionId = Integer.parseInt(minionId[i]);
            final PreparedStatement increaseMinionAgeStatement = connection.prepareStatement(UPDATE_MINION_NAME_AND_AGE);
            increaseMinionAgeStatement.setInt(1, currentMinionId);
            increaseMinionAgeStatement.executeUpdate();
        }

        final PreparedStatement getAllMinionsStatement = connection.prepareStatement(MINION_NAMES_ID);
        ResultSet allMinionsSet = getAllMinionsStatement.executeQuery();

        while (allMinionsSet.next()) {
            String minionName = allMinionsSet.getString("name");
            int minionAge = allMinionsSet.getInt("age");
            System.out.printf(PRINT_FORMAT, minionName, minionAge);
        }
    }
}