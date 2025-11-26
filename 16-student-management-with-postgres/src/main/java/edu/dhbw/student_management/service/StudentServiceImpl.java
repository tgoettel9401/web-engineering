package edu.dhbw.student_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student existing = getStudentById(id);
        existing.setFirstName(studentDetails.getFirstName());
        existing.setLastName(studentDetails.getLastName());
        existing.setMatrikelnummer(studentDetails.getMatrikelnummer());
        return studentRepository.save(existing);
    }

    @Override
    public ResponseEntity<String> deleteStudent(Long id) {
        Student existing = studentRepository.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        studentRepository.delete(existing);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
