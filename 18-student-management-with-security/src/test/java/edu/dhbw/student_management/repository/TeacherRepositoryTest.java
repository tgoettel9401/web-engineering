package edu.dhbw.student_management.repository;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void teacherCourseRelationship() {
        Teacher t = new Teacher(null, "Karl", "Heinz", "E100");
        teacherRepository.saveAndFlush(t);

        Course c = new Course(null, "Physik");
        t.addCourse(c);
        courseRepository.saveAndFlush(c);

        Teacher loaded = teacherRepository.findById(t.getId()).orElseThrow();
        assertEquals(1, loaded.getCourses().size());
        assertEquals(c.getId(), loaded.getCourses().get(0).getId());
    }
}
