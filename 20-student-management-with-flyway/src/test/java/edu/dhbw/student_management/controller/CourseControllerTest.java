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

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.service.CourseService;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    @Test
    void getAllCourses_ShouldReturnList() throws Exception {
        Course c = new Course(1L, "Web Engineering");
        when(courseService.findAll()).thenReturn(Arrays.asList(c));

        mockMvc.perform(get("/api/v2/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Web Engineering"));
    }

    @Test
    void getAllCourses_ShouldReturnEmptyList() throws Exception {
        when(courseService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getCourseById_ShouldReturnCourse_WhenFound() throws Exception {
        Course c = new Course(1L, "Web Engineering");
        when(courseService.findById(1L)).thenReturn(c);

        mockMvc.perform(get("/api/v2/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Web Engineering"));
    }

    @Test
    void getCourseById_ShouldReturn404_WhenNotFound() throws Exception {
        when(courseService.findById(99L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v2/courses/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() throws Exception {
        Course saved = new Course(2L, "Databases");
        when(courseService.create(any(Course.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v2/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Databases\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Databases"));
    }

    @Test
    void updateCourse_ShouldReturnUpdatedCourse_WhenFound() throws Exception {
        Course updated = new Course(1L, "Advanced Web Engineering");
        when(courseService.update(eq(1L), any(Course.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v2/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Advanced Web Engineering\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Web Engineering"));
    }

    @Test
    void updateCourse_ShouldReturn404_WhenNotFound() throws Exception {
        when(courseService.update(eq(99L), any(Course.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/api/v2/courses/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Ghost Course\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCourse_ShouldReturnNoContent_WhenDeleted() throws Exception {
        mockMvc.perform(delete("/api/v2/courses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCourse_ShouldReturnNotFound_WhenNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(courseService).delete(99L);

        mockMvc.perform(delete("/api/v2/courses/99"))
                .andExpect(status().isNotFound());
    }
}
