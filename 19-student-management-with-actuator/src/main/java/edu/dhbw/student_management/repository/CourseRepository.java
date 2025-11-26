package edu.dhbw.student_management.repository;

import edu.dhbw.student_management.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
