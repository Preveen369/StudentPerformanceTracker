package studentperformancetracker.dao;

import studentperformancetracker.db.DBConnection;
import studentperformancetracker.models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public boolean addSubject(Subject subject) {
        String sql = "INSERT INTO Subjects (subject_code, subject_name, credits) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subject.getSubjectCode());
            stmt.setString(2, subject.getSubjectName());
            stmt.setInt(3, subject.getCredits());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error adding subject: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSubject(Subject subject) {
        String sql = "UPDATE Subjects SET subject_name = ?, credits = ? WHERE subject_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subject.getSubjectName());
            stmt.setInt(2, subject.getCredits());
            stmt.setString(3, subject.getSubjectCode());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[WARN] Failed to update subject: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSubject(String subjectCode) {
        String sql = "DELETE FROM Subjects WHERE subject_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subjectCode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[WARN] Failed to delete subject: " + e.getMessage());
            return false;
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM Subjects";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                subjects.add(new Subject(
                    rs.getString("subject_code"),
                    rs.getString("subject_name"),
                    rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[WARN] Error fetching subjects: " + e.getMessage());
        }
        return subjects;
    }

    public Subject getSubjectByCode(String code) {
        String sql = "SELECT * FROM Subjects WHERE subject_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Subject(
                        rs.getString("subject_code"),
                        rs.getString("subject_name"),
                        rs.getInt("credits")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[WARN] Lookup failed for subject: " + e.getMessage());
        }
        return null;
    }
}
