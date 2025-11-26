package edu.dhbw.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllReturnsList() {
        Student s1 = new Student(1L, "A", "B", "m1");
        Student s2 = new Student(2L, "C", "D", "m2");
        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getFirstName());
    }

    @Test
    void getStudentByIdFound() {
        Student s = new Student(5L, "X", "Y", "m5");
        when(studentRepository.findById(5L)).thenReturn(Optional.of(s));

        Student found = studentService.getStudentById(5L);
        assertEquals("X", found.getFirstName());
    }

    @Test
    void getStudentByIdNotFoundThrows() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> studentService.getStudentById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void createStudentSaves() {
        Student s = new Student(null, "New", "User", "m777");
        Student saved = new Student(10L, "New", "User", "m777");
        when(studentRepository.save(s)).thenReturn(saved);

        Student result = studentService.createStudent(s);
        assertEquals(10L, result.getId());
    }

    @Test
    void updateStudentUpdates() {
        Student existing = new Student(2L, "Old", "Name", "m2");
        Student update = new Student(null, "New", "Name", "m2");
        when(studentRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student res = studentService.updateStudent(2L, update);
        assertEquals("New", res.getFirstName());
    }

    @Test
    void updateStudentNotFoundThrows() {
        Student update = new Student(null, "New", "Name", "m2");
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> studentService.updateStudent(99L, update));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteStudentReturnsNoContentWhenExists() {
        Student existing = new Student(3L, "D", "E", "m3");
        when(studentRepository.findById(3L)).thenReturn(Optional.of(existing));

        ResponseEntity<String> resp = studentService.deleteStudent(3L);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        verify(studentRepository).delete(existing);
    }

    @Test
    void deleteStudentReturnsNotFoundWhenNotExists() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<String> resp = studentService.deleteStudent(99L);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        verify(studentRepository, never()).delete(any());
    }
}
