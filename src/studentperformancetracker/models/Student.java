package studentperformancetracker.models;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String name;
    private LocalDate dob;
    private String department;
    
    public Student(int studentId, String name, LocalDate dob, String department){
        this.studentId = studentId;
        this.name = name;
        this.dob  = dob;
        this.department  = department;
    }
    
    // Getters and Setters
    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public LocalDate getDob() { return dob; }
    public String getDepartment() { return department; }

    public void setName(String name) { this.name = name; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return studentId + " | " + name + " | " + dob + " | " + department;
    }
    
}
