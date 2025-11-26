package edu.dhbw.student_management.service;

import edu.dhbw.student_management.entity.Teacher;
import edu.dhbw.student_management.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Teacher update(Long id, Teacher teacherDetails) {
        Teacher existing = findById(id);
        existing.setFirstName(teacherDetails.getFirstName());
        existing.setLastName(teacherDetails.getLastName());
        existing.setEmployeeNumber(teacherDetails.getEmployeeNumber());
        return teacherRepository.save(existing);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        Teacher existing = findById(id);
        teacherRepository.delete(existing);
    }
}
