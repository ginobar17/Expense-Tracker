import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:finance_tracker.db";

        System.out.println("Testing database connection...");

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                System.out.println("✓ Successfully connected to database!");
                System.out.println("Database: " + dbUrl);
            }
        } catch (SQLException e) {
            System.err.println("✗ Connection failed: " + e.getMessage());
        }
    }
}
