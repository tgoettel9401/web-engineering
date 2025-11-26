-- Flyway migration to create initial schema for Student Management
-- Sequences
CREATE SEQUENCE IF NOT EXISTS student_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS room_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS course_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS teacher_seq START WITH 1;

-- Users table (uses IDENTITY / auto-increment)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255)
);

-- Teachers
CREATE TABLE IF NOT EXISTS teachers (
    id BIGINT DEFAULT NEXT VALUE FOR teacher_seq PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    employee_number VARCHAR(255) NOT NULL UNIQUE
);

-- Rooms
CREATE TABLE IF NOT EXISTS rooms (
    id BIGINT DEFAULT NEXT VALUE FOR room_seq PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255),
    capacity INTEGER
);

-- Students
CREATE TABLE IF NOT EXISTS students (
    id BIGINT DEFAULT NEXT VALUE FOR student_seq PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    matrikelnummer VARCHAR(255) NOT NULL UNIQUE
);

-- Courses
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT DEFAULT NEXT VALUE FOR course_seq PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    teacher_id BIGINT,
    room_id BIGINT,
    CONSTRAINT fk_course_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id),
    CONSTRAINT fk_course_room FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- Join table for students <-> courses many-to-many
CREATE TABLE IF NOT EXISTS student_course (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_sc_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_sc_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

-- Indexes to help lookups
CREATE INDEX IF NOT EXISTS idx_students_matrikelnummer ON students(matrikelnummer);
CREATE INDEX IF NOT EXISTS idx_teachers_employee_number ON teachers(employee_number);
