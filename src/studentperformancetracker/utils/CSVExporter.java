package studentperformancetracker.utils;

import studentperformancetracker.models.Grade;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    public static boolean exportGradesToCSV(int studentId, List<Grade> grades, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Student ID,Subject Code,Marks Obtained\n");
            for (Grade grade : grades) {
                writer.append(grade.getStudentId() + ",");
                writer.append(grade.getSubjectCode() + ",");
                writer.append(String.valueOf(grade.getMarksObtained()));
                writer.append("\n");
            }
            System.out.println("[OK] Report exported to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR] Export failed: " + e.getMessage());
            return false;
        }
    }
}
