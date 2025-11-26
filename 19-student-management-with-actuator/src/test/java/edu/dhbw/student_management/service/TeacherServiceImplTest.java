package edu.dhbw.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import edu.dhbw.student_management.entity.Teacher;
import edu.dhbw.student_management.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private UsernamePasswordAuthenticationToken adminAuth = new UsernamePasswordAuthenticationToken(
            "admin",
            "",
            java.util.List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void findAllReturnsList() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        Teacher t1 = new Teacher(1L, "A", "B", "EMP1");
        Teacher t2 = new Teacher(2L, "C", "D", "EMP2");
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Teacher> res = teacherService.findAll();
        assertEquals(2, res.size());
    }

    @Test
    void findByIdFound() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        Teacher t = new Teacher(1L, "Alice", "Wonderland", "EMP001");
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(t));

        Teacher found = teacherService.findById(1L);
        assertEquals("Alice", found.getFirstName());
    }

    @Test
    void findByIdNotFoundThrows() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> teacherService.findById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void createTeacherSaves() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        Teacher t = new Teacher(null, "Bob", "Builder", "EMP002");
        Teacher saved = new Teacher(10L, "Bob", "Builder", "EMP002");
        when(teacherRepository.save(t)).thenReturn(saved);

        Teacher result = teacherService.create(t);
        assertEquals(10L, result.getId());
    }

    @Test
    void updateTeacherUpdates() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        Teacher existing = new Teacher(1L, "Old", "Name", "EMP001");
        Teacher update = new Teacher(null, "New", "Name", "EMP001");
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(teacherRepository.save(any(Teacher.class))).thenAnswer(i -> i.getArgument(0));

        Teacher res = teacherService.update(1L, update);
        assertEquals("New", res.getFirstName());
    }

    @Test
    void updateTeacherNotFoundThrows() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        Teacher update = new Teacher(null, "New", "Name", "EMP001");
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> teacherService.update(99L, update));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteTeacherDeletes() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        Teacher existing = new Teacher(1L, "To Delete", "User", "EMP003");
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existing));

        teacherService.delete(1L);
        verify(teacherRepository).delete(existing);
    }

    @Test
    void deleteTeacherNotFoundThrows() {
        SecurityContextHolder.getContext().setAuthentication(adminAuth);
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> teacherService.delete(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void findAllWithoutAdminThrowsForbidden() {

        // Test with User (not Admin)
        UsernamePasswordAuthenticationToken nonAdmin = new UsernamePasswordAuthenticationToken(
                "user",
                "",
                java.util.List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(nonAdmin);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> teacherService.findAll());
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
    }
}
