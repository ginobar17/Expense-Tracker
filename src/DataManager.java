import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DATABASE_URL = "jdbc:sqlite:finance_tracker.db";

    // Establish database connection
    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return null;
        }
    }

    // Initialize users table
    public static void initializeUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✓ Users table initialized");
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
        }
    }

    // Initialize transactions table
    public static void initializeTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT NOT NULL," +
                "category TEXT NOT NULL," +
                "notes TEXT," +
                "amount REAL NOT NULL," +
                "transaction_date TEXT NOT NULL," +
                "payment_method TEXT," +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✓ Transactions table initialized");
        } catch (SQLException e) {
            System.err.println("Error creating transactions table: " + e.getMessage());
        }
    }

    // Add default admin user
    public static void createDefaultUser() {
        String sql = "INSERT OR IGNORE INTO users(username, password) VALUES(?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "admin");
            pstmt.setString(2, "admin123");
            pstmt.executeUpdate();
            System.out.println("✓ Default user created");
        } catch (SQLException e) {
            System.err.println("Error creating default user: " + e.getMessage());
        }
    }

    // Verify user credentials
    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }

    // Insert new transaction
    public static boolean insertTransaction(String type, String category, String notes,
                                            double amount, String date, String paymentMethod) {
        String sql = "INSERT INTO transactions(type, category, notes, amount, transaction_date, payment_method) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, category);
            pstmt.setString(3, notes);
            pstmt.setDouble(4, amount);
            pstmt.setString(5, date);
            pstmt.setString(6, paymentMethod);
            pstmt.executeUpdate();
            System.out.println("✓ Transaction added: " + type + " - " + category);
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all transactions
    public static List<Transaction> fetchAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_id, type, category, notes, amount, transaction_date, payment_method " +
                "FROM transactions ORDER BY transaction_id DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getString("notes"),
                        rs.getDouble("amount"),
                        rs.getString("transaction_date"),
                        rs.getString("payment_method")
                ));
            }
            System.out.println("✓ Loaded " + transactions.size() + " transactions");
        } catch (SQLException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }

    // Remove transaction by ID
    public static boolean removeTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("✓ Transaction deleted");
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
            return false;
        }
    }
}
