package studentperformancetracker.services;

import studentperformancetracker.dao.StudentDAO;
import studentperformancetracker.models.Student;

import java.time.LocalDate;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public boolean registerStudent(int id, String name, String dobString, String department) {
        try {
            LocalDate dob = LocalDate.parse(dobString);
            Student student = new Student(id, name.trim(), dob, department.trim().toUpperCase());
            return studentDAO.addStudent(student);
        } catch (Exception e) {
            System.err.println("[WARN] Invalid input: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public boolean updateStudent(Student student) {
        return studentDAO.updateStudent(student);
    }

    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }
}
