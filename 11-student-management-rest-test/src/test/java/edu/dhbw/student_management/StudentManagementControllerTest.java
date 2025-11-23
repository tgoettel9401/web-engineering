package edu.dhbw.student_management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.dhbw.student_management.controller.StudentController;
import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentService;

public class StudentManagementControllerTest {

    private StudentController studentController;
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        Student student = new Student(1L, "Student ", "1", "00001");
        Student student2 = new Student(2L, "Student 2", "2", "00002");
        Student student3 = new Student(3L, "Student 3", "3", "00003");
        Student student4 = new Student(4L, "Student 4", "4", "00004");
        List<Student> students = new ArrayList<>(Arrays.asList(student, student2, student3, student4));
        studentService = new StudentService(student, students);
        studentController = new StudentController(studentService);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = studentController.getAllStudents();
        assert students.size() == 4;
    }

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student(null, "New Student", "5", "00005");
        Student createdStudent = studentController.createStudent(newStudent);
        assert createdStudent.getFirstName().equals("New Student");
    }

    @Test
    public void testGetStudentById() {
        Student student = studentController.getStudentById(1L);
        assert student.getId() == 1L;
    }

    @Test
    public void testUpdateStudent() {
        Student updatedDetails = new Student(null, "Updated Student", "1", "00001");
        Student updatedStudent = studentController.updateStudent(1L, updatedDetails);
        assert updatedStudent.getFirstName().equals("Updated Student");
    }

    @Test
    public void testDeleteStudent() {
        studentController.deleteStudent(1L);
        List<Student> students = studentController.getAllStudents();
        assert students.size() == 3;
    }

}
