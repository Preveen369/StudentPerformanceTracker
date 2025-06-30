package studentperformancetracker.services;

import studentperformancetracker.dao.GradeDAO;
import studentperformancetracker.dao.SubjectDAO;
import studentperformancetracker.models.Grade;
import studentperformancetracker.models.Subject;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ReportService {
    private final GradeDAO gradeDAO;
    private final SubjectDAO subjectDAO;

    public ReportService() {
        this.gradeDAO = new GradeDAO();
        this.subjectDAO = new SubjectDAO();
    }

    public double calculateGPA(List<Grade> grades) {
        double totalCredits = 0, totalWeightedPoints = 0;
        for (Grade g : grades) {
            Subject subject = subjectDAO.getSubjectByCode(g.getSubjectCode());
            if (subject != null) {
                int credits = subject.getCredits();
                int marks = g.getMarksObtained();
                double gradePoint = getGradePoint(marks);
                totalWeightedPoints += gradePoint * credits;
                totalCredits += credits;
            }
        }
        return totalCredits == 0 ? 0 : totalWeightedPoints / totalCredits;
    }

    private double getGradePoint(int marks) {
        if (marks >= 90) return 10;
        if (marks >= 80) return 9;
        if (marks >= 70) return 8;
        if (marks >= 60) return 7;
        if (marks >= 50) return 6;
        if (marks >= 40) return 5;
        return 0;
    }

    public Map<String, String> generateGradeSummary(List<Grade> grades) {
        Map<String, String> summary = new HashMap<>();
        for (Grade g : grades) {
            double gradePoint = getGradePoint(g.getMarksObtained());
            summary.put(g.getSubjectCode(), "Marks: " + g.getMarksObtained() + ", Grade Point: " + gradePoint);
        }
        return summary;
    }
}
