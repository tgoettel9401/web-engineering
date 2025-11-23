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
import edu.dhbw.student_management.service.StudentServiceDelegate;
import edu.dhbw.student_management.service.StudentServiceInterface;

class StudentControllerDelegationTest {

    private StudentServiceDelegate delegate;
    private StudentServiceInterface serviceV1;
    private StudentServiceInterface serviceV2;
    private StudentController controller;

    @BeforeEach
    void setUp() {
        delegate = mock(StudentServiceDelegate.class);
        serviceV1 = mock(StudentServiceInterface.class);
        serviceV2 = mock(StudentServiceInterface.class);
        controller = new StudentController(delegate);
    }

    @Test
    void getAllDelegatesToCorrectServiceDependingOnVersion() {
        List<Student> v1List = Collections.singletonList(new Student(11L, "V1", "User", "m11"));
        List<Student> v2List = Collections.singletonList(new Student(22L, "V2", "User", "m22"));

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(delegate.getForVersion("v2")).thenReturn(serviceV2);

        when(serviceV1.getAllStudents()).thenReturn(v1List);
        when(serviceV2.getAllStudents()).thenReturn(v2List);

        List<Student> res1 = controller.getAllStudents("v1");
        verify(delegate).getForVersion("v1");
        verify(serviceV1).getAllStudents();
        verifyNoInteractions(serviceV2);
        assertSame(v1List, res1);

        List<Student> res2 = controller.getAllStudents("v2");
        verify(delegate).getForVersion("v2");
        verify(serviceV2).getAllStudents();
        verifyNoMoreInteractions(serviceV1);
        assertSame(v2List, res2);
    }

    @Test
    void createDelegatesToCorrectServiceDependingOnVersion() {
        Student inputV1 = new Student(null, "V1New", "Student", "m100");
        Student returnedV1 = new Student(100L, "V1New", "Student", "m100");

        Student inputV2 = new Student(null, "Anna", "Test", "m1");
        Student returnedV2 = new Student(5L, "Anna", "Test", "m1");

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(delegate.getForVersion("v2")).thenReturn(serviceV2);

        when(serviceV1.createStudent(inputV1)).thenReturn(returnedV1);
        when(serviceV2.createStudent(inputV2)).thenReturn(returnedV2);

        Student r1 = controller.createStudent("v1", inputV1);
        verify(delegate).getForVersion("v1");
        verify(serviceV1).createStudent(inputV1);
        assertEquals(100L, r1.getId());

        Student r2 = controller.createStudent("v2", inputV2);
        verify(delegate).getForVersion("v2");
        verify(serviceV2).createStudent(inputV2);
        assertEquals(5L, r2.getId());
    }

    @Test
    void getByIdDelegatesToCorrectServiceDependingOnVersion() {
        Student s1 = new Student(21L, "V1", "User", "m21");
        Student s2 = new Student(2L, "B", "C", "m2");

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(delegate.getForVersion("v2")).thenReturn(serviceV2);

        when(serviceV1.getStudentById(21L)).thenReturn(s1);
        when(serviceV2.getStudentById(2L)).thenReturn(s2);

        Student r1 = controller.getStudentById("v1", 21L);
        verify(delegate).getForVersion("v1");
        verify(serviceV1).getStudentById(21L);
        assertEquals(21L, r1.getId());

        Student r2 = controller.getStudentById("v2", 2L);
        verify(delegate).getForVersion("v2");
        verify(serviceV2).getStudentById(2L);
        assertEquals(2L, r2.getId());
    }

    @Test
    void deleteDelegatesToCorrectServiceDependingOnVersion() {
        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(delegate.getForVersion("v2")).thenReturn(serviceV2);

        when(serviceV1.deleteStudent(13L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(""));
        when(serviceV2.deleteStudent(3L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(""));

        ResponseEntity<String> resp1 = controller.deleteStudent("v1", 13L);
        verify(delegate).getForVersion("v1");
        verify(serviceV1).deleteStudent(13L);
        assertEquals(HttpStatus.NO_CONTENT, resp1.getStatusCode());

        ResponseEntity<String> resp2 = controller.deleteStudent("v2", 3L);
        verify(delegate).getForVersion("v2");
        verify(serviceV2).deleteStudent(3L);
        assertEquals(HttpStatus.NO_CONTENT, resp2.getStatusCode());
    }
}
