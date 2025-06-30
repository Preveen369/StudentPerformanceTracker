package studentperformancetracker.dao;

import studentperformancetracker.db.DBConnection;
import studentperformancetracker.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO Students (student_id, name, dob, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getName());
            stmt.setDate(3, Date.valueOf(student.getDob()));
            stmt.setString(4, student.getDepartment());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to add student: " + e.getMessage());
            return false;
        }
    }
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getDate("dob").toLocalDate(),
                    rs.getString("department")
                );
                students.add(s);
            }
        } catch (SQLException e) {
            System.err.println("[WARN] Error fetching students: " + e.getMessage());
        }
        return students;
    }
    
    public boolean deleteStudent(int studentId){
        String sql = "DELETE FROM Students WHERE student_id = ?";
        try(Connection conn = DBConnection.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)){
              stmt.setInt(1, studentId);
              return stmt.executeUpdate() > 0;
        } catch(SQLException e){
              System.err.println("[WARN] Could not delete student: " + e.getMessage());
              return false;
        }
    }
    
 public boolean updateStudent(Student student) {
        String sql = "UPDATE Students SET name = ?, dob = ?, department = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setDate(2, Date.valueOf(student.getDob()));
            stmt.setString(3, student.getDepartment());
            stmt.setInt(4, student.getStudentId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[WARN] Could not update student: " + e.getMessage());
            return false;
        }
    }
}
