package edu.dhbw.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllReturnsList() {
        Course c1 = new Course(1L, "A");
        Course c2 = new Course(2L, "B");
        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Course> res = courseService.findAll();
        assertEquals(2, res.size());
    }

    @Test
    void findByIdFound() {
        Course c = new Course(1L, "Web");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(c));

        Course found = courseService.findById(1L);
        assertEquals("Web", found.getName());
    }

    @Test
    void findByIdNotFoundThrows() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> courseService.findById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void createCourseSaves() {
        Course c = new Course(null, "New Course");
        Course saved = new Course(10L, "New Course");
        when(courseRepository.save(c)).thenReturn(saved);

        Course result = courseService.create(c);
        assertEquals(10L, result.getId());
    }

    @Test
    void updateCourseUpdates() {
        Course existing = new Course(1L, "Old Name");
        Course update = new Course(null, "New Name");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        Course res = courseService.update(1L, update);
        assertEquals("New Name", res.getName());
    }

    @Test
    void updateCourseNotFoundThrows() {
        Course update = new Course(null, "New Name");
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> courseService.update(99L, update));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteCourseDeletes() {
        Course existing = new Course(1L, "To Delete");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(existing));

        courseService.delete(1L);
        verify(courseRepository).delete(existing);
    }

    @Test
    void deleteCourseNotFoundThrows() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> courseService.delete(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
