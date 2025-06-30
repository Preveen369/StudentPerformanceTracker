package studentperformancetracker.menu;

import studentperformancetracker.services.StudentService;
import studentperformancetracker.services.ReportService;
import studentperformancetracker.dao.GradeDAO;
import studentperformancetracker.dao.SubjectDAO;
import studentperformancetracker.models.Student;
import studentperformancetracker.models.Subject;
import studentperformancetracker.models.Grade;
import studentperformancetracker.utils.CSVExporter;
import studentperformancetracker.utils.InputHelper;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class MenuController {
    private final StudentService studentService = new StudentService();
    private final ReportService reportService = new ReportService();

    public void start() {
        while (true) {
            System.out.println("\n===== STUDENT PERFORMANCE TRACKER =====");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Subjects");
            System.out.println("3. Record Grades");
            System.out.println("4. Generate Reports");
            System.out.println("5. Exit");
            String choice = InputHelper.readString("Select an option: ");

            switch (choice) {
                case "1" -> manageStudents();
                case "2" -> manageSubjects();
                case "3" -> recordGrades();
                case "4" -> generateReports();
                case "5" -> exitApp();
                default -> System.out.println("[ERROR] Invalid input. Please try again.");
            }
        }
    }

    private void manageStudents() {
        while (true) {
            System.out.println("\n--- STUDENT MANAGEMENT ---");
            System.out.println("1. Register New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Back to Main Menu");
            String option = InputHelper.readString("Choose an option: ");

            switch (option) {
                case "1" -> {
                    int id = InputHelper.readInt("Enter Student ID: ");
                    String name = InputHelper.readString("Enter Name: ");
                    String dob = InputHelper.readDate("Enter Date of Birth").toString();
                    String dept = InputHelper.readString("Enter Department: ");
                    boolean added = studentService.registerStudent(id, name, dob, dept);
                    System.out.println(added ? "[OK] Student added." : "[WARN] Failed to add student.");
                }
                case "2" -> {
                    List<Student> students = studentService.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("[WARN] No student records found.");
                    } else {
                        System.out.println("[INFO] Registered Students:");
                        System.out.println("+------------+----------------------+------------+-------------+");
                        System.out.println("| Student ID | Name                 | DOB        | Department  |");
                        System.out.println("+------------+----------------------+------------+-------------+");
                        for (Student s : students) {
                            System.out.printf("| %-10d | %-20s | %-10s | %-11s |\n",
                                s.getStudentId(), s.getName(), s.getDob(), s.getDepartment());
                        }
                        System.out.println("+------------+----------------------+------------+-------------+");
                    }
                }
                case "3" -> {
                    int id = InputHelper.readInt("Enter Student ID to update: ");
                    List<Student> all = studentService.getAllStudents();
                    Student existing = all.stream()
                        .filter(s -> s.getStudentId() == id)
                        .findFirst()
                        .orElse(null);

                    if (existing == null) {
                        System.out.println("[WARN] No student found with ID: " + id);
                        break;
                    }

                    System.out.println("[INFO] Leave fields blank to retain current values.");

                    String newName = InputHelper.readString("New Name (" + existing.getName() + "): ");
                    String newDobStr = InputHelper.readString("New DOB (" + existing.getDob() + ") [yyyy-MM-dd]: ");
                    String newDept = InputHelper.readString("New Dept (" + existing.getDepartment() + "): ");

                    if (!newName.isEmpty()) existing.setName(newName);
                    if (!newDobStr.isEmpty()) {
                        try {
                            existing.setDob(LocalDate.parse(newDobStr));
                        } catch (Exception e) {
                            System.out.println("[WARN] Invalid date. Keeping existing DOB.");
                        }
                    }
                    if (!newDept.isEmpty()) existing.setDepartment(newDept.toUpperCase());

                    boolean updated = studentService.updateStudent(existing);
                    System.out.println(updated ? "[OK] Student updated." : "[ERROR] Update failed.");
                }

                case "4" -> {
                    int id = InputHelper.readInt("Enter Student ID to delete: ");
                    String confirm = InputHelper.readYesNo("Are you sure you want to delete student ID " + id + "?");
                    if (confirm.equals("yes")) {
                        boolean deleted = studentService.deleteStudent(id);
                        System.out.println(deleted ? "[OK] Student deleted." : "[ERROR] Delete failed.");
                    } else {
                        System.out.println("[INFO] Delete cancelled.");
                    }
                }

                case "5" -> { return; }
                default -> System.out.println("[ERROR] Invalid option.");
            }
        }
    }

    private void manageSubjects() {
        while (true) {
            System.out.println("\n--- SUBJECT MANAGEMENT ---");
            System.out.println("1. Add Subject");
            System.out.println("2. View All Subjects");
            System.out.println("3. Update Subject");
            System.out.println("4. Delete Subject");
            System.out.println("5. Back to Main Menu");
            String option = InputHelper.readString("Choose an option: ");

            switch (option) {
                case "1" -> {
                    String code = InputHelper.readString("Enter Subject Code: ");
                    String name = InputHelper.readString("Enter Subject Name: ");
                    int credits = InputHelper.readInt("Enter Credits: ");
                    boolean added = new SubjectDAO().addSubject(new Subject(code, name, credits));
                    System.out.println(added ? "[OK] Subject added." : "[ERROR] Failed to add subject.");
                }
                
                case "2" -> {
                    List<Subject> subjects = new SubjectDAO().getAllSubjects();
                    if (subjects.isEmpty()) {
                        System.out.println("[WARN] No subject records found.");
                    } else {
                        System.out.println("[INFO] Subject Catalog:");
                        System.out.println("+--------------+----------------------+---------+");
                        System.out.println("| Subject Code | Subject Name         | Credits |");
                        System.out.println("+--------------+----------------------+---------+");
                        for (Subject sub : subjects) {
                            System.out.printf("| %-12s | %-20s | %-7d |\n",
                                sub.getSubjectCode(), sub.getSubjectName(), sub.getCredits());
                        }
                        System.out.println("+--------------+----------------------+---------+");
                    }
                }

                case "3" -> {
                    String code = InputHelper.readString("Enter Subject Code to Update: ");
                    Subject existing = new SubjectDAO().getSubjectByCode(code);
                    if (existing == null) {
                        System.out.println("[ERROR] Subject not found.");
                        break;
                    }
                    String name = InputHelper.readString("Enter New Name: ");
                    int credits = InputHelper.readInt("Enter New Credits: ");
                    existing.setSubjectName(name);
                    existing.setCredits(credits);
                    boolean updated = new SubjectDAO().updateSubject(existing);
                    System.out.println(updated ? "[OK] Subject updated." : "[WARN] Failed to update subject.");
                }
                
                case "4" -> {
                    String code = InputHelper.readString("Enter Subject Code to Delete: ");
                    String confirm = InputHelper.readYesNo("Are you sure you want to delete subject '" + code + "'?");
                    if (confirm.equals("yes")) {
                        boolean deleted = new SubjectDAO().deleteSubject(code);
                        System.out.println(deleted ? "[OK] Subject deleted." : "[WARN] Failed to delete subject.");
                    } else {
                        System.out.println("[INFO] Delete cancelled.");
                    }
                }

                case "5" -> { return; }
                default -> System.out.println("[ERROR] Invalid option.");
            }
        }
    }

    private void recordGrades() {
        System.out.println("\n--- GRADE ENTRY ---");
        System.out.println("1. Add/Update Grade");
        System.out.println("2. Delete Grade");
        System.out.println("3. Back to Main Menu");
        String option = InputHelper.readString("Choose an option: ");

        switch (option) {
            case "1" -> {
                int studentId = InputHelper.readInt("Enter Student ID: ");
                String subjectCode = InputHelper.readString("Enter Subject Code: ");
                int marks = InputHelper.readInt("Enter Marks: ");
                Grade grade = new Grade(studentId, subjectCode, marks);
                boolean saved = new GradeDAO().addOrUpdateGrade(grade);
                System.out.println(saved ? "[OK] Grade saved." : "[ERROR] Failed to save grade.");
            }
            case "2" -> {
                int studentId = InputHelper.readInt("Enter Student ID: ");
                String subjectCode = InputHelper.readString("Enter Subject Code: ");
                String confirm = InputHelper.readYesNo("Confirm delete grade for student " + studentId + " in subject '" + subjectCode + "'?");
                if (confirm.equals("yes")) {
                    boolean deleted = new GradeDAO().deleteGrade(studentId, subjectCode);
                    System.out.println(deleted ? "[OK] Grade deleted." : "[WARN] Failed to delete grade.");
                } else {
                    System.out.println("[INFO] Delete cancelled.");
                }
            }
            case "3" -> { return; }
            default -> System.out.println("[ERROR] Invalid option.");
        }
    }

    private void generateReports() {
        int studentId = InputHelper.readInt("Enter Student ID to view performance: ");
        List<Grade> grades = new GradeDAO().getGradesByStudent(studentId);
        if (grades.isEmpty()) {
            System.out.println("[WARN] No grade records found.");
            return;
        }

        Map<String, String> summary = reportService.generateGradeSummary(grades);
        System.out.println("+----------------------+---------------------------------+");
        System.out.println("| Subject  Code        | Grade Details                   |");
        System.out.println("+----------------------+---------------------------------+");
        for (Map.Entry<String, String> entry : summary.entrySet()) {
            System.out.printf("| %-20s | %-23s    |\n", entry.getKey(), entry.getValue());
        }
        System.out.println("+----------------------+---------------------------------+");


        double gpa = reportService.calculateGPA(grades);
        System.out.printf("[REPORT] CGPA: %.2f\n\n", gpa);

        String confirm = InputHelper.readYesNo("[FILE] Export report to CSV?");
        if (confirm.equals("yes")) {
            String filePath = "student_report_" + studentId + ".csv";
            CSVExporter.exportGradesToCSV(studentId, grades, filePath);
        }
    }

    private void exitApp() {
        System.out.println("[INFO] Exiting... Goodbye.");
        System.exit(0);
    }
}
