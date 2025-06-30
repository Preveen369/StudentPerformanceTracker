package studentperformancetracker.dao;

import studentperformancetracker.db.DBConnection;
import studentperformancetracker.models.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public boolean addOrUpdateGrade(Grade grade) {
        String sql = """
            INSERT INTO Grades (student_id, subject_code, marks_obtained)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE marks_obtained = VALUES(marks_obtained)
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getStudentId());
            stmt.setString(2, grade.getSubjectCode());
            stmt.setInt(3, grade.getMarksObtained());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[WARN] Error adding/updating grade: " + e.getMessage());
            return false;
        }
    }

    public List<Grade> getGradesByStudent(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM Grades WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    grades.add(new Grade(
                        rs.getInt("student_id"),
                        rs.getString("subject_code"),
                        rs.getInt("marks_obtained")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to fetch grades: " + e.getMessage());
        }
        return grades;
    }

    public boolean deleteGrade(int studentId, String subjectCode) {
        String sql = "DELETE FROM Grades WHERE student_id = ? AND subject_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, subjectCode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[WARN]️ Could not delete grade: " + e.getMessage());
            return false;
        }
    }
}
