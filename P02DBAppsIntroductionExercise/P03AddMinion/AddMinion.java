import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Utils.Utils;

public class AddMinion {
    private static final String GET_TOWN_BY_NAME = "SELECT t.`id` FROM `towns` AS t WHERE t.`name` = ?";
    private static final String INSERT_INTO_TOWNS = "INSERT INTO `towns`(`name`) VALUES(?)";
    private static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";
    private static final String GET_VILLAIN_BY_NAME = "SELECT v.`name` FROM `villains` As v WHERE v.`name` = ?";
    private static final String INSERT_INTO_VILLAINS = "INSERT INTO `villains`(`name`, `evilness_factor`) VALUES(?, ?)";
    private static final String VILLAIN_ADDED_FORMAT = "Villain %s was added to the database.%n";
    private static final String VILLAIN_EVIL_FACTOR = "evil";
    private static final String INSERT_INTO_MINIONS = "INSERT INTO `minions`(`name`, `age`, `town_id`) VALUES(?, ?, ?)";
    private static final String COLUMN_LABEL_ID = "id";
    private static final String INSERT_INTO_MINIONS_VILLAINS = "INSERT INTO `minions_villains`(`minion_id`, `villain_id`) VALUES(?, ?)";
    private static final String GET_MINION_ID = "SELECT m.`id` FROM `minions` AS m WHERE m.`name` = ?";
    private static final String GET_VILLAIN_ID = "SELECT v.`id` FROM `villains` AS v WHERE v.`name` = ?";
    private static final String MINION_ADDED_FORMAT = "Successfully added %s to be minion of %s.";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getSQLConnection();

        final String[] minionInfo = scanner.nextLine().split(" ");

        final String minionName = minionInfo[1];
        final int minionAge = Integer.parseInt(minionInfo[2]);
        final String minionTown = minionInfo[3];
        final String villainName = scanner.nextLine().split(" ")[1];

        final PreparedStatement townStatement = connection.prepareStatement(GET_TOWN_BY_NAME);
        townStatement.setString(1, minionTown);

        final ResultSet townSet = townStatement.executeQuery();

        if (!townSet.next()) {
            final PreparedStatement insertTownStatement = connection.prepareStatement(INSERT_INTO_TOWNS);

            insertTownStatement.setString(1, minionTown);

            insertTownStatement.executeUpdate();

            System.out.printf(TOWN_ADDED_FORMAT, minionTown);
        }

        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_BY_NAME);
        villainStatement.setString(1, villainName);

        final ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            final PreparedStatement insertVillainStatement = connection.prepareStatement(INSERT_INTO_VILLAINS);

            insertVillainStatement.setString(1, villainName);
            insertVillainStatement.setString(2, VILLAIN_EVIL_FACTOR);

            insertVillainStatement.executeUpdate();

            System.out.printf(VILLAIN_ADDED_FORMAT, villainName);
        }

        //find again town id
        townStatement.setString(1, minionTown);
        final ResultSet newTownSet = townStatement.executeQuery();
        newTownSet.next();
        final int townId = newTownSet.getInt(COLUMN_LABEL_ID);
        //insert into minions
        final PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_INTO_MINIONS);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, townId);
        insertMinionStatement.executeUpdate();

        //1.Get minion id
        final PreparedStatement getMinionIdStatement = connection.prepareStatement(GET_MINION_ID);
        getMinionIdStatement.setString(1, minionName);
        ResultSet minionIdSet = getMinionIdStatement.executeQuery();
        minionIdSet.next();
        int minionId = minionIdSet.getInt(COLUMN_LABEL_ID);
        //2.Get villain id
        final PreparedStatement getVillainIdStatement = connection.prepareStatement(GET_VILLAIN_ID);
        getVillainIdStatement.setString(1, villainName);
        ResultSet villainIdSet = getVillainIdStatement.executeQuery();
        villainIdSet.next();
        int villainId = villainIdSet.getInt(COLUMN_LABEL_ID);

        //insert into minions_villains
        final PreparedStatement insertIntoMinionsVillains = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
        insertIntoMinionsVillains.setInt(1, minionId);
        insertIntoMinionsVillains.setInt(2, villainId);
        insertIntoMinionsVillains.executeUpdate();

        System.out.printf(MINION_ADDED_FORMAT, minionName, villainName);

        connection.close();
    }
}