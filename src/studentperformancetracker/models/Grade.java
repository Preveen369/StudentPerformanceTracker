package studentperformancetracker.models;

public class Grade {
    private int studentId;
    private String subjectCode;
    private int marksObtained;

    public Grade(int studentId, String subjectCode, int marksObtained) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.marksObtained = marksObtained;
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public String getSubjectCode() { return subjectCode; }
    public int getMarksObtained() { return marksObtained; }

    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }

    @Override
    public String toString() {
        return "Student: " + studentId + ", Subject: " + subjectCode + ", Marks: " + marksObtained;
    }
}
