package edu.dhbw.student_management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentServiceDelegate;
import edu.dhbw.student_management.service.StudentServiceInterface;

@WebMvcTest(StudentController.class)
class StudentControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentServiceDelegate delegate;

    @MockitoBean
    private StudentServiceInterface serviceV1;

    @MockitoBean
    private StudentServiceInterface serviceV2;

    // ========== V1 Tests ==========

    @Test
    void v1GetAllReturnsStudentsList() throws Exception {
        Student s1 = new Student(1L, "John", "Doe", "m123");
        Student s2 = new Student(2L, "Jane", "Smith", "m456");

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(serviceV1.getAllStudents()).thenReturn(Arrays.asList(s1, s2));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].matrikelnummer").value("m123"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].matrikelnummer").value("m456"));

        verify(delegate).getForVersion("v1");
        verify(serviceV1).getAllStudents();
    }

    @Test
    void v1GetByIdReturnsStudent() throws Exception {
        Student student = new Student(10L, "Alice", "Wonder", "m999");

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(serviceV1.getStudentById(10L)).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Wonder"))
                .andExpect(jsonPath("$.matrikelnummer").value("m999"));

        verify(delegate).getForVersion("v1");
        verify(serviceV1).getStudentById(10L);
    }

    @Test
    void v1CreateStudentReturnsCreated() throws Exception {
        Student created = new Student(99L, "Bob", "Builder", "m777");

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(serviceV1.createStudent(any(Student.class))).thenReturn(created);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Bob\",\"lastName\":\"Builder\",\"matrikelnummer\":\"m777\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Builder"))
                .andExpect(jsonPath("$.matrikelnummer").value("m777"));

        verify(delegate).getForVersion("v1");
        verify(serviceV1).createStudent(any(Student.class));
    }

    @Test
    void v1UpdateStudentReturnsUpdated() throws Exception {
        Student updated = new Student(5L, "Charlie", "Updated", "m888");

        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(serviceV1.updateStudent(eq(5L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/students/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Charlie\",\"lastName\":\"Updated\",\"matrikelnummer\":\"m888\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.firstName").value("Charlie"))
                .andExpect(jsonPath("$.lastName").value("Updated"));

        verify(delegate).getForVersion("v1");
        verify(serviceV1).updateStudent(eq(5L), any(Student.class));
    }

    @Test
    void v1DeleteStudentReturnsNoContent() throws Exception {
        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(serviceV1.deleteStudent(7L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(""));

        mockMvc.perform(delete("/api/v1/students/7"))
                .andExpect(status().isNoContent());

        verify(delegate).getForVersion("v1");
        verify(serviceV1).deleteStudent(7L);
    }

    @Test
    void v1GetAllReturnsEmptyList() throws Exception {
        when(delegate.getForVersion("v1")).thenReturn(serviceV1);
        when(serviceV1.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(delegate).getForVersion("v1");
        verify(serviceV1).getAllStudents();
    }

    // ========== V2 Tests ==========

    @Test
    void v2GetAllReturnsStudentsList() throws Exception {
        Student s1 = new Student(1L, "John", "Doe", "m123");
        Student s2 = new Student(2L, "Jane", "Smith", "m456");

        when(delegate.getForVersion("v2")).thenReturn(serviceV2);
        when(serviceV2.getAllStudents()).thenReturn(Arrays.asList(s1, s2));

        mockMvc.perform(get("/api/v2/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].matrikelnummer").value("m123"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].matrikelnummer").value("m456"));

        verify(delegate).getForVersion("v2");
        verify(serviceV2).getAllStudents();
    }

    @Test
    void v2GetByIdReturnsStudent() throws Exception {
        Student student = new Student(10L, "Alice", "Wonder", "m999");

        when(delegate.getForVersion("v2")).thenReturn(serviceV2);
        when(serviceV2.getStudentById(10L)).thenReturn(student);

        mockMvc.perform(get("/api/v2/students/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Wonder"))
                .andExpect(jsonPath("$.matrikelnummer").value("m999"));

        verify(delegate).getForVersion("v2");
        verify(serviceV2).getStudentById(10L);
    }

    @Test
    void v2CreateStudentReturnsCreated() throws Exception {
        Student created = new Student(99L, "Bob", "Builder", "m777");

        when(delegate.getForVersion("v2")).thenReturn(serviceV2);
        when(serviceV2.createStudent(any(Student.class))).thenReturn(created);

        mockMvc.perform(post("/api/v2/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Bob\",\"lastName\":\"Builder\",\"matrikelnummer\":\"m777\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Builder"))
                .andExpect(jsonPath("$.matrikelnummer").value("m777"));

        verify(delegate).getForVersion("v2");
        verify(serviceV2).createStudent(any(Student.class));
    }

    @Test
    void v2UpdateStudentReturnsUpdated() throws Exception {
        Student updated = new Student(5L, "Charlie", "Updated", "m888");

        when(delegate.getForVersion("v2")).thenReturn(serviceV2);
        when(serviceV2.updateStudent(eq(5L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v2/students/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Charlie\",\"lastName\":\"Updated\",\"matrikelnummer\":\"m888\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.firstName").value("Charlie"))
                .andExpect(jsonPath("$.lastName").value("Updated"));

        verify(delegate).getForVersion("v2");
        verify(serviceV2).updateStudent(eq(5L), any(Student.class));
    }

    @Test
    void v2DeleteStudentReturnsNoContent() throws Exception {
        when(delegate.getForVersion("v2")).thenReturn(serviceV2);
        when(serviceV2.deleteStudent(7L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(""));

        mockMvc.perform(delete("/api/v2/students/7"))
                .andExpect(status().isNoContent());

        verify(delegate).getForVersion("v2");
        verify(serviceV2).deleteStudent(7L);
    }

    @Test
    void v2GetAllReturnsEmptyList() throws Exception {
        when(delegate.getForVersion("v2")).thenReturn(serviceV2);
        when(serviceV2.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(delegate).getForVersion("v2");
        verify(serviceV2).getAllStudents();
    }
}
