# 📘 Student Performance Tracker (Console-Based Java & MySQL Application)

## 📄 1. Project Overview

The *Student Performance Tracker* is a lightweight, console-based Java application designed to streamline academic data management without relying on external frameworks. Powered by a MySQL backend via JDBC, it provides a structured, menu-driven interface for handling student records, subject-credit mappings, grade entries, and GPA/CGPA calculations. Ideal for local or institutional use, the system emphasizes modularity, clarity, and offline operability—making it both an educational tool and a practical solution for academic workflows.

---

## 🎯 2. Objectives

- Design a clean, interactive console interface for academic operations  
- Simplify CRUD operations for students, subjects, and grade entries  
- Automate GPA/CGPA computation using marks and credit weights  
- Enable seamless CSV export of student performance reports  
- Follow modular Java architecture and JDBC best practices  

---

## 🧱 3. System Architecture

### 🔹 Layered Design

| Layer     | Description                                  |
|-----------|----------------------------------------------|
| `db`      | JDBC-based connection module with MySQL      |
| `models`  | POJOs: Student, Subject, and Grade           |
| `dao`     | Direct DB access logic (SQL interaction)     |
| `services`| Business logic and GPA calculation routines  |
| `menu`    | User interaction loop and console menu logic |
| `utils`   | Input validation, CSV export, banner display |

---

## 🧪 4. Functional Modules

| Module              | Responsibilities                                          |
|---------------------|-----------------------------------------------------------|
| Student Management  | Add/update/delete/view student records                    |
| Subject Handling    | Manage subject codes, titles, and credit values           |
| Grade Entry         | Record or edit marks per student-subject                  |
| GPA/CGPA Engine     | Calculate weighted GPA based on marks and credits         |
| Reports             | Summarize GPA per subject or student                      |
| CSV Export          | Export academic reports in `.csv` format                  |

---

## 📂 5. Database Schema

**Database Name:** `student_performance_db`

```sql
CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    dob DATE,
    department VARCHAR(50)
);

CREATE TABLE Subjects (
    subject_code VARCHAR(10) PRIMARY KEY,
    subject_name VARCHAR(100),
    credits INT
);

CREATE TABLE Grades (
    student_id INT,
    subject_code VARCHAR(10),
    marks_obtained INT,
    PRIMARY KEY (student_id, subject_code),
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (subject_code) REFERENCES Subjects(subject_code)
);
```

---

## 🖥️ 6. Sample Output (Console Snapshot)

```
===== STUDENT PERFORMANCE TRACKER =====
1. Manage Students
2. Manage Subjects
3. Record Grades
4. Generate Reports
5. Exit
```

---

## 🚀 7. How to Run

1. Install **Java (JDK 17+)** and **MySQL Server**
2. Create the database and tables using the provided `schema.sql`
3. Configure database credentials in `DBConnection.java`
4. Build and run the project using an IDE like NetBeans or from the terminal:

```bash
javac App.java
java App
```

---

## 🧾 8. CSV Export Example

A sample file will be saved as:

```
student_report_1001.csv
```

Containing:

```
Student ID,Subject Code,Marks Obtained
1001,MATH101,88
1001,PHY102,77
1001,CHE103,92
```

---

## 🤝 Contributing

Pull requests are welcome! Feel free to fork the repository and submit improvements.

**Contributions are welcome! Follow these steps:**
1. Fork the project.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature description"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## 📧 Contact

For queries or suggestions:
- 📧 Email: spreveen123@gmail.com
- 🌐 LinkedIn: www.linkedin.com/in/preveen-s-17250529b/

---

## 🌟 Show your support

If you find this project helpful or interesting, please consider giving it a ⭐ on GitHub to show your support!

---


