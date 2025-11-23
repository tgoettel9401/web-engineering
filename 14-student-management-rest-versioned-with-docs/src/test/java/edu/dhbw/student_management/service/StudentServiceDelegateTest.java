package edu.dhbw.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class StudentServiceDelegateTest {

    @Test
    void getForVersionReturnsCorrectServiceForV1AndV2() {
        StudentServiceInterface v1 = mock(StudentServiceInterface.class);
        StudentServiceInterface v2 = mock(StudentServiceInterface.class);

        Map<String, StudentServiceInterface> beans = new HashMap<>();
        beans.put("studentServiceV1", v1);
        beans.put("studentServiceV2", v2);

        StudentServiceDelegate delegate = new StudentServiceDelegate(beans);

        assertSame(v1, delegate.getForVersion("v1"));
        assertSame(v2, delegate.getForVersion("v2"));
        assertSame(v1, delegate.getForVersion("V1"));
        assertSame(v2, delegate.getForVersion("V2"));
    }

    @Test
    void getForVersionThrowsNotFoundForUnknownVersion() {
        StudentServiceInterface v1 = mock(StudentServiceInterface.class);
        Map<String, StudentServiceInterface> beans = new HashMap<>();
        beans.put("studentServiceV1", v1);

        StudentServiceDelegate delegate = new StudentServiceDelegate(beans);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> delegate.getForVersion("v99"));
        assertTrue(ex.getMessage().contains("API version not supported"));
    }

    @Test
    void getForVersionThrowsBadRequestForNullVersion() {
        Map<String, StudentServiceInterface> beans = new HashMap<>();
        StudentServiceDelegate delegate = new StudentServiceDelegate(beans);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> delegate.getForVersion(null));
        assertTrue(ex.getMessage().contains("version is required"));
    }
}
