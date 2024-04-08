import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {
    private static final String GET_ALL_MINIONS = "SELECT `name` FROM `minions`";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        PreparedStatement statement = connection.prepareStatement(GET_ALL_MINIONS);
        ResultSet resultSet = statement.executeQuery();

        List<String> names = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        while (resultSet.next()){
            String name = resultSet.getString("name");
            names.add(name);
        }

        for (int i = 0; i < names.size() / 2; i++) {
            sb.append(names.get(i)).append(System.lineSeparator());
            sb.append(names.get(names.size() - 1 - i)).append(System.lineSeparator());

        }
        System.out.println(sb.toString().trim());
    }
}
