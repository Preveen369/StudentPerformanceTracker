package studentperformancetracker;

import studentperformancetracker.db.DBConnection;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            // Connection succeeded!
            System.out.println("Connection succeeded!");
        }
        DBConnection.closeConnection();
    }
}
