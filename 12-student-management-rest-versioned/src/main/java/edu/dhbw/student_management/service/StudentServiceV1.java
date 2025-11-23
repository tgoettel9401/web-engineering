package edu.dhbw.student_management.service;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import edu.dhbw.student_management.entity.Student;

@Service
public class StudentServiceV1 implements StudentServiceInterface {

    private final Student student;
    private final List<Student> students;

    public StudentServiceV1(Student student, List<Student> students) {
        this.student = student;
        this.students = students;
    }

    @Override
    public List<Student> getAllStudents() {
        return students;
    }

    @Override
    public Student createStudent(Student student) {
        return student;
    }

    @Override
    public Student getStudentById(Long id) {
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        return student;
    }

    @Override
    public ResponseEntity<String> deleteStudent(Long id) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }

}
