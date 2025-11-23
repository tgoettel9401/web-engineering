package edu.dhbw.student_management.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class StudentServiceDelegate {

    private final Map<String, StudentServiceInterface> servicesByVersion = new HashMap<>();

    public StudentServiceDelegate(Map<String, StudentServiceInterface> services) {
        services.forEach((beanName, svc) -> {
            String n = beanName.toLowerCase();
            if (n.contains("v1")) {
                servicesByVersion.put("v1", svc);
            } else if (n.contains("v2")) {
                servicesByVersion.put("v2", svc);
            }
        });
    }

    public StudentServiceInterface getForVersion(String version) {
        if (version == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "version is required");
        }
        StudentServiceInterface svc = servicesByVersion.get(version.toLowerCase());
        if (svc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "API version not supported: " + version);
        }
        return svc;
    }
}
