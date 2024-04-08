import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    private static final String GET_VILLAIN_BY_ID = "SELECT v.`name` FROM `villains` AS v WHERE `id` = ?";
    private static final String GET_MINION_COUNT_BY_VILLAINS_ID =
            "SELECT COUNT(mv.`minion_id`) AS `minion_count` FROM `minions_villains` AS mv WHERE mv.`villain_id` = ?";
    private static final String DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID = "DELETE FROM `minions_villains` WHERE `villain_id` = ?";
    private static final String DELETE_VILLAIN_BY_ID = "DELETE FROM `villains` WHERE `id` = ?";
    private static final String DELETE_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETE_COUNT_MINIONS_FORMAT = "%d minions released";
    private static final String NO_SUCH_VILLAIN = "No such villain was found";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = Utils.getSQLConnection();
        int villainId = Integer.parseInt(scanner.nextLine());

        final PreparedStatement selectedVillain = connection.prepareStatement(GET_VILLAIN_BY_ID);
        selectedVillain.setInt(1, villainId);
        final ResultSet villainSet = selectedVillain.executeQuery();

        if (!villainSet.next()){
            System.out.println(NO_SUCH_VILLAIN);
            return;
        }

        final String villainName = villainSet.getString("name");
        final PreparedStatement selectAllMinions = connection.prepareStatement(GET_MINION_COUNT_BY_VILLAINS_ID);
        selectAllMinions.setInt(1,villainId);
        final ResultSet countMinionsSet = selectAllMinions.executeQuery();
        countMinionsSet.next();

        final int countDeletedMinions = countMinionsSet.getInt("minion_count");

        connection.setAutoCommit(false);
        try(
            PreparedStatement deleteMinionStatement = connection.prepareStatement(DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID);
            PreparedStatement deleteVillainStatement = connection.prepareStatement(DELETE_VILLAIN_BY_ID)) {

            deleteMinionStatement.setInt(1, villainId);
            deleteMinionStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();
            connection.commit();
            System.out.printf(DELETE_VILLAIN_FORMAT,villainName);
            System.out.printf(DELETE_COUNT_MINIONS_FORMAT,countDeletedMinions);

        } catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();
    }
}
