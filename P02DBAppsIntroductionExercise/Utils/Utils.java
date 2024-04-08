import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Utils {
    ;

    public static Connection getSQLConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password", "1122");
       return DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",properties);
    }
}
