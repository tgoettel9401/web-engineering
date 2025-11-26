package edu.dhbw.student_management.service;

import edu.dhbw.student_management.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course findById(Long id);

    Course create(Course course);

    Course update(Long id, Course courseDetails);

    void delete(Long id);
}
