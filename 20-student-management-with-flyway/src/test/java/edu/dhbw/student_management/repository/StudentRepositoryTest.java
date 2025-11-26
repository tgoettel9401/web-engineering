package edu.dhbw.student_management.repository;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void saveAndFindStudent() {
        Student s = new Student(null, "Max", "Mustermann", "M123");
        studentRepository.saveAndFlush(s);

        assertNotNull(s.getId());

        Student loaded = studentRepository.findById(s.getId()).orElseThrow();
        assertEquals("Max", loaded.getFirstName());
        assertEquals("Mustermann", loaded.getLastName());
        assertEquals("M123", loaded.getMatrikelnummer());
    }

    @Test
    void uniqueMatrikelnummerConstraint() {
        Student s1 = new Student(null, "A", "B", "M999");
        studentRepository.saveAndFlush(s1);

        Student s2 = new Student(null, "C", "D", "M999");
        assertThrows(DataIntegrityViolationException.class, () -> studentRepository.saveAndFlush(s2));
    }

    @Test
    void manyToManyRelationWithCourse() {
        Course c = new Course(null, "Mathematik");
        courseRepository.saveAndFlush(c);

        Student s = new Student(null, "Anna", "Schmidt", "S456");
        s.addCourse(c);
        studentRepository.saveAndFlush(s);

        Course loadedCourse = courseRepository.findById(c.getId()).orElseThrow();
        assertEquals(1, loadedCourse.getStudents().size());
        assertEquals(s.getId(), loadedCourse.getStudents().get(0).getId());
    }

}
