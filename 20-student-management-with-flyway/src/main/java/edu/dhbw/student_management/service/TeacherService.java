package edu.dhbw.student_management.service;

import edu.dhbw.student_management.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(Long id);

    Teacher create(Teacher teacherDetails);

    Teacher update(Long id, Teacher teacherDetails);

    void delete(Long id);
}
