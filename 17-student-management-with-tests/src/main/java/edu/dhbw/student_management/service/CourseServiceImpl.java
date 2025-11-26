package edu.dhbw.student_management.service;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Long id, Course courseDetails) {
        Course existing = findById(id);
        existing.setName(courseDetails.getName());
        existing.setTeacher(courseDetails.getTeacher());
        existing.setRoom(courseDetails.getRoom());
        return courseRepository.save(existing);
    }

    public void delete(Long id) {
        Course existing = findById(id);
        courseRepository.delete(existing);
    }
}
