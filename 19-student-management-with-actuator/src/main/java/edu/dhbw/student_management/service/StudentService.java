package edu.dhbw.student_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import edu.dhbw.student_management.entity.Student;

public interface StudentService {

    List<Student> getAllStudents();

    Student createStudent(Student student);

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student studentDetails);

    ResponseEntity<String> deleteStudent(Long id);
}
