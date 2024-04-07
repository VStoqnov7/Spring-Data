import Utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class GetMinionNames {

    private static final String GET_MINIONS_NAME_AGE_AND_BY_VILLAIN =
            "SELECT v.`name` AS `villain_name`,\n" +
                    "m.`name` AS `minion_name`,\n" +
                    "m.`age`\n" +
                    "FROM `villains` AS v\n" +
                    "JOIN `minions_villains` AS mv\n" +
                    "ON v.`id` = mv.`villain_id`\n" +
                    "JOIN `minions` AS m\n" +
                    "ON mv.`minion_id` = m.`id`\n" +
                    "WHERE v.`id` = ?;";
    private static final String NO_VILLAIN_FORMAT = "No villain with ID %d exists in the database.";
    private static final String VILLAIN_NAME_FORMAT = "Villain: %s";
    private static final String MINION_NAME_AGE_FORMAT = "%d. %s %d";


    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getSQLConnection();

        PreparedStatement statement = connection.prepareStatement(GET_MINIONS_NAME_AGE_AND_BY_VILLAIN);
        int villainId = Integer.parseInt(scanner.nextLine());

        statement.setInt(1, villainId);

        ResultSet resultSet = statement.executeQuery();
        StringBuilder sb = new StringBuilder();
        int index = 1;

        if (!resultSet.next()){
            System.out.printf(NO_VILLAIN_FORMAT,villainId);
            connection.close();
            return;
        }else {
            final String villainName = resultSet.getString("villain_name");
            sb.append(String.format(VILLAIN_NAME_FORMAT, villainName)).append(System.lineSeparator());
            do {
                final String minionName = resultSet.getString("minion_name");
                final int minionAge = resultSet.getInt("age");
                sb.append(String.format(MINION_NAME_AGE_FORMAT, index++, minionName, minionAge)).append(System.lineSeparator());
            } while (resultSet.next());

        }

        System.out.println(sb.toString().trim());
        connection.close();
    }

}
