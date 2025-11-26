package edu.dhbw.student_management.repository;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void studentEnrollmentPersists() {
        Course c = new Course(null, "Datenbanken");
        courseRepository.saveAndFlush(c);

        Student s = new Student(null, "Lisa", "Meyer", "S789");
        s.addCourse(c);
        studentRepository.saveAndFlush(s);

        Course loaded = courseRepository.findById(c.getId()).orElseThrow();
        assertEquals(1, loaded.getStudents().size());
        assertEquals(s.getId(), loaded.getStudents().get(0).getId());
    }
}
