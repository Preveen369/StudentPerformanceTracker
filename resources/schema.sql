CREATE TABLE IF NOT EXISTS Students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    dob DATE,
    department VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Subjects (
    subject_code VARCHAR(10) PRIMARY KEY,
    subject_name VARCHAR(100),
    credits INT
);

CREATE TABLE IF NOT EXISTS Grades (
    student_id INT,
    subject_code VARCHAR(10),
    marks_obtained INT,
    PRIMARY KEY (student_id, subject_code),
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (subject_code) REFERENCES Subjects(subject_code)
);
