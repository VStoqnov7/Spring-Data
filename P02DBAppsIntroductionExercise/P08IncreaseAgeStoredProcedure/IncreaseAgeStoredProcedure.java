import Utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {

    private static final String CALL_PROCEDURE = "CALL usp_get_older(?)";
    private static final String SELECT_NAME_AGE_FOR_MINIONS = "SELECT `name`, `age` FROM `minions` WHERE `id` = ?";

    private static final String PRINT_FORMANT = "Name: %s%nAge: %d";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int minionId = Integer.parseInt(scanner.nextLine());
        Connection connection = Utils.getSQLConnection();
        PreparedStatement callProcedureStatement = connection.prepareStatement(CALL_PROCEDURE);
        callProcedureStatement.setInt(1,minionId);
        callProcedureStatement.executeUpdate();

        PreparedStatement selectNameAndAgeStatement = connection.prepareStatement(SELECT_NAME_AGE_FOR_MINIONS);
        selectNameAndAgeStatement.setInt(1,minionId);
        ResultSet resultNameAgeSet = selectNameAndAgeStatement.executeQuery();

        if (!resultNameAgeSet.next()) {
            System.out.println("No such minion id.");
        } else {
            String name = resultNameAgeSet.getString("name");
            int age = resultNameAgeSet.getInt("age");
            System.out.printf(PRINT_FORMANT,name,age);
        }
    }
}
