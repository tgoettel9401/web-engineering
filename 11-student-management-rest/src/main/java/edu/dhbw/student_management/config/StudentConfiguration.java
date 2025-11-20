package edu.dhbw.student_management.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Student;

@Configuration
public class StudentConfiguration {

    @Bean
    public Student student() {
        Student student = new Student(1L, "Max", "Mustermann", "228839");
        return student;
    }

    @Bean
    public List<Student> students() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Max", "Mustermann", "228839"));
        students.add(new Student(2L, "Maja", "Musterfrau", "228899"));
        students.add(new Student(3L, "Moritz", "Hans", "228844"));
        students.forEach(student -> {
            student.addCourse(initializeCourse1());
            student.addCourse(initializeCourse2());
        });
        return students;
    }

    private Course initializeCourse1() {
        return new Course(1L, "Web-Engineering");
    }

    private Course initializeCourse2() {
        return new Course(2L, "Algorithmen");
    }

}
