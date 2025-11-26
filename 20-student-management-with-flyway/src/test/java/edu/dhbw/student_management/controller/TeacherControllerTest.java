package edu.dhbw.student_management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import edu.dhbw.student_management.entity.Teacher;
import edu.dhbw.student_management.service.TeacherService;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService teacherService;

    @Test
    void getAllTeachers_ShouldReturnList() throws Exception {
        Teacher t = new Teacher(1L, "Alice", "Wonderland", "EMP001");
        when(teacherService.findAll()).thenReturn(Arrays.asList(t));

        mockMvc.perform(get("/api/v2/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].lastName").value("Wonderland"))
                .andExpect(jsonPath("$[0].employeeNumber").value("EMP001"));
    }

    @Test
    void getAllTeachers_ShouldReturnEmptyList() throws Exception {
        when(teacherService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTeacherById_ShouldReturnTeacher_WhenFound() throws Exception {
        Teacher t = new Teacher(1L, "Alice", "Wonderland", "EMP001");
        when(teacherService.findById(1L)).thenReturn(t);

        mockMvc.perform(get("/api/v2/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    void getTeacherById_ShouldReturn404_WhenNotFound() throws Exception {
        when(teacherService.findById(99L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v2/teachers/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTeacher_ShouldReturnCreatedTeacher() throws Exception {
        Teacher saved = new Teacher(2L, "Bob", "Builder", "EMP002");
        when(teacherService.create(any(Teacher.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v2/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Bob\",\"lastName\":\"Builder\",\"employeeNumber\":\"EMP002\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Bob"));
    }

    @Test
    void updateTeacher_ShouldReturnUpdatedTeacher_WhenFound() throws Exception {
        Teacher updated = new Teacher(1L, "Alice", "Smith", "EMP001");
        when(teacherService.update(eq(1L), any(Teacher.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v2/teachers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Alice\",\"lastName\":\"Smith\",\"employeeNumber\":\"EMP001\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void updateTeacher_ShouldReturn404_WhenNotFound() throws Exception {
        when(teacherService.update(eq(99L), any(Teacher.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/api/v2/teachers/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Ghost\",\"lastName\":\"Teacher\",\"employeeNumber\":\"EMP000\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeacher_ShouldReturnNoContent_WhenDeleted() throws Exception {
        mockMvc.perform(delete("/api/v2/teachers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTeacher_ShouldReturnNotFound_WhenNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(teacherService).delete(99L);

        mockMvc.perform(delete("/api/v2/teachers/99"))
                .andExpect(status().isNotFound());
    }
}
