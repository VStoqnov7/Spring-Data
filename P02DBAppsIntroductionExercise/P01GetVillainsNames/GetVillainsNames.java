import Utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    private static final String GET_VILLAINS_NAMES =
            "SELECT v.`name`,\n" +
            "COUNT(DISTINCT mv.`minion_id`) AS `minions_count`\n" +
            "FROM `villains` AS v\n" +
            "JOIN `minions_villains` AS mv\n" +
            "ON v.`id` = mv.`villain_id`\n" +
            "GROUP BY mv.`villain_id`\n" +
            "HAVING `minions_count` > ?\n" +
            "ORDER BY `minions_count`";
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_MINIONS_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);

        statement.setInt(1,15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            final String villainName = resultSet.getString(COLUMN_LABEL_NAME);
            final int minionsCount = resultSet.getInt(COLUMN_LABEL_MINIONS_COUNT);

            System.out.printf(PRINT_FORMAT,villainName,minionsCount);

        }
        connection.close();
    }
}
