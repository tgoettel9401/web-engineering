package edu.dhbw.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class StudentServiceFactoryTest {

    @Test
    void returnsCorrectServiceForVersions() {
        StudentServiceInterface v1 = mock(StudentServiceInterface.class);
        StudentServiceInterface v2 = mock(StudentServiceInterface.class);

        Map<String, StudentServiceInterface> beans = new HashMap<>();
        beans.put("studentServiceV1", v1);
        beans.put("studentServiceV2", v2);

        StudentServiceFactory factory = new StudentServiceFactory(beans);

        assertSame(v1, factory.getForVersion("v1"));
        assertSame(v2, factory.getForVersion("v2"));
    }

    @Test
    void throwsOnUnknownVersion() {
        StudentServiceInterface v1 = mock(StudentServiceInterface.class);
        Map<String, StudentServiceInterface> beans = new HashMap<>();
        beans.put("studentServiceV1", v1);

        StudentServiceFactory factory = new StudentServiceFactory(beans);

        assertThrows(ResponseStatusException.class, () -> factory.getForVersion("v3"));
    }
}
