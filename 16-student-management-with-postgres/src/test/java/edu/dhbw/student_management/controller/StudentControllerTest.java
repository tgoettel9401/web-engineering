package edu.dhbw.student_management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.web.server.ResponseStatusException;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void getAllStudents_ShouldReturnList() throws Exception {
        Student s1 = new Student(1L, "John", "Doe", "m123");
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(s1));

        mockMvc.perform(get("/api/v2/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].matrikelnummer").value("m123"));
    }

    @Test
    void getAllStudents_ShouldReturnEmptyList() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getStudentById_ShouldReturnStudent_WhenFound() throws Exception {
        Student s1 = new Student(1L, "John", "Doe", "m123");
        when(studentService.getStudentById(1L)).thenReturn(s1);

        mockMvc.perform(get("/api/v2/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getStudentById_ShouldReturn404_WhenNotFound() throws Exception {
        when(studentService.getStudentById(99L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v2/students/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        Student saved = new Student(2L, "Jane", "Doe", "m456");
        when(studentService.createStudent(any(Student.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v2/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"matrikelnummer\":\"m456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent_WhenFound() throws Exception {
        Student updated = new Student(1L, "John", "Smith", "m123");
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v2/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Smith\",\"matrikelnummer\":\"m123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void updateStudent_ShouldReturn404_WhenNotFound() throws Exception {
        when(studentService.updateStudent(eq(99L), any(Student.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/api/v2/students/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Ghost\",\"lastName\":\"User\",\"matrikelnummer\":\"m000\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_ShouldReturnNoContent_WhenDeleted() throws Exception {
        when(studentService.deleteStudent(1L)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v2/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_ShouldReturnNotFound_WhenNotFound() throws Exception {
        when(studentService.deleteStudent(99L)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(delete("/api/v2/students/99"))
                .andExpect(status().isNotFound());
    }
}
