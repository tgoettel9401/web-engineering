package edu.dhbw.student_management.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    public void testGetStudentById_Found() throws Exception {
        Student s = new Student(1L, "Max", "Mustermann", "228839");
        when(studentService.getStudentById(eq(1L))).thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetStudentById_NotFound() throws Exception {
        when(studentService.getStudentById(eq(999L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/students/999"))
                .andExpect(status().isNotFound());
    }

}
