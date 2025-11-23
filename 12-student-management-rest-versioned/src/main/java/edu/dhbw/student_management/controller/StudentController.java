package edu.dhbw.student_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentServiceDelegate;

@RestController
@RequestMapping("/api/{version}/students")
public class StudentController {

    private final StudentServiceDelegate delegate;

    public StudentController(StudentServiceDelegate delegate) {
        this.delegate = delegate;
    }

    @GetMapping
    public List<Student> getAllStudents(@PathVariable String version) {
        return delegate.getForVersion(version).getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String version, @PathVariable Long id) {
        return delegate.getForVersion(version).getStudentById(id);
    }

    @PostMapping
    public Student createStudent(@PathVariable String version, @RequestBody Student student) {
        return delegate.getForVersion(version).createStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable String version, @PathVariable Long id,
            @RequestBody Student studentDetails) {
        return delegate.getForVersion(version).updateStudent(id, studentDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String version, @PathVariable Long id) {
        return delegate.getForVersion(version).deleteStudent(id);
    }
}
