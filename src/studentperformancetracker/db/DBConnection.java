package studentperformancetracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_performance_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnection() {}  // prevent instantiation

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            return null;
        }
    }
}
