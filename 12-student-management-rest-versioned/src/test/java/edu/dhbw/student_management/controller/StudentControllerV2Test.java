package edu.dhbw.student_management.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentServiceFactory;
import edu.dhbw.student_management.service.StudentServiceInterface;

class StudentControllerV2Test {

    private StudentServiceFactory factory;
    private StudentServiceInterface service;
    private StudentControllerDelegating controller;

    @BeforeEach
    void setUp() {
        factory = mock(StudentServiceFactory.class);
        service = mock(StudentServiceInterface.class);
        controller = new StudentControllerDelegating(factory);
    }

    @Test
    void getAllDelegatesToFactoryAndService() {
        List<Student> empty = Collections.emptyList();
        when(factory.getForVersion("v2")).thenReturn(service);
        when(service.getAllStudents()).thenReturn(empty);

        List<Student> result = controller.getAllStudents("v2");

        verify(factory).getForVersion("v2");
        verify(service).getAllStudents();
        assertSame(empty, result);
    }

    @Test
    void createDelegatesToFactoryAndService() {
        Student input = new Student(null, "Anna", "Test", "m1");
        Student returned = new Student(5L, "Anna", "Test", "m1");
        when(factory.getForVersion("v2")).thenReturn(service);
        when(service.createStudent(input)).thenReturn(returned);

        Student result = controller.createStudent("v2", input);

        verify(factory).getForVersion("v2");
        verify(service).createStudent(input);
        assertEquals(5L, result.getId());
    }

    @Test
    void getByIdDelegatesToFactoryAndService() {
        Student s = new Student(2L, "B", "C", "m2");
        when(factory.getForVersion("v2")).thenReturn(service);
        when(service.getStudentById(2L)).thenReturn(s);

        Student result = controller.getStudentById("v2", 2L);

        verify(factory).getForVersion("v2");
        verify(service).getStudentById(2L);
        assertEquals(2L, result.getId());
    }

    @Test
    void deleteDelegatesToFactoryAndService() {
        when(factory.getForVersion("v2")).thenReturn(service);
        when(service.deleteStudent(3L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(""));

        ResponseEntity<String> resp = controller.deleteStudent("v2", 3L);

        verify(factory).getForVersion("v2");
        verify(service).deleteStudent(3L);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }
}
