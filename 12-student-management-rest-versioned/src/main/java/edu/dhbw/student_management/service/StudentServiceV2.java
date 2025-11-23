package edu.dhbw.student_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.dhbw.student_management.entity.Student;

@Service
public class StudentServiceV2 implements StudentServiceInterface {

    private final Map<Long, Student> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public StudentServiceV2() {
        Student s = new Student(idGenerator.getAndIncrement(), "Max", "Mustermann", "123456");
        store.put(s.getId(), s);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Student createStudent(Student student) {
        Long id = idGenerator.getAndIncrement();
        student.setId(id);
        store.put(id, student);
        return student;
    }

    @Override
    public Student getStudentById(Long id) {
        Student s = store.get(id);
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return s;
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student existing = store.get(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        existing.setFirstName(studentDetails.getFirstName());
        existing.setLastName(studentDetails.getLastName());
        existing.setMatrikelnummer(studentDetails.getMatrikelnummer());
        return existing;
    }

    @Override
    public ResponseEntity<String> deleteStudent(Long id) {
        Student removed = store.remove(id);
        if (removed == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
