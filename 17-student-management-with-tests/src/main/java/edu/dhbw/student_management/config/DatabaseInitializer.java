package edu.dhbw.student_management.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Room;
import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.entity.Teacher;
import edu.dhbw.student_management.service.CourseService;
import edu.dhbw.student_management.service.RoomService;
import edu.dhbw.student_management.service.StudentService;
import edu.dhbw.student_management.service.TeacherService;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final RoomService roomService;

    public DatabaseInitializer(StudentService studentService, CourseService courseService,
            TeacherService teacherService, RoomService roomService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.roomService = roomService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create teachers
        Teacher t1 = new Teacher(null, "Anna", "Lehmann", "EMP-001");
        Teacher t2 = new Teacher(null, "Boris", "Keller", "EMP-002");
        t1 = teacherService.create(t1);
        t2 = teacherService.create(t2);

        // Create rooms
        Room r1 = new Room(null, "R101", "Building A", 30);
        Room r2 = new Room(null, "R202", "Building B", 25);
        r1 = roomService.create(r1);
        r2 = roomService.create(r2);

        // Create courses and assign teacher+room
        Course c1 = new Course(null, "Web-Engineering");
        c1.setTeacher(t1);
        c1.setRoom(r1);
        c1 = courseService.create(c1);

        Course c2 = new Course(null, "Algorithmen");
        c2.setTeacher(t2);
        c2.setRoom(r2);
        c2 = courseService.create(c2);

        // Create students and enroll them
        Student s1 = new Student(null, "Max", "Mustermann", "228839");
        s1.addCourse(c1);
        s1.addCourse(c2);
        studentService.createStudent(s1);

        Student s2 = new Student(null, "Maja", "Musterfrau", "228899");
        s2.addCourse(c1);
        studentService.createStudent(s2);

        Student s3 = new Student(null, "Moritz", "Hans", "228844");
        s3.addCourse(c2);
        studentService.createStudent(s3);
    }

}
