package edu.dhbw.student_management.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Room;
import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.entity.Teacher;
import edu.dhbw.student_management.service.CourseService;
import edu.dhbw.student_management.service.RoomService;
import edu.dhbw.student_management.service.StudentService;
import edu.dhbw.student_management.service.TeacherService;
import edu.dhbw.student_management.entity.User;
import edu.dhbw.student_management.service.UserService;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final RoomService roomService;
    private final UserService userService;

    public DatabaseInitializer(StudentService studentService, CourseService courseService,
            TeacherService teacherService, RoomService roomService, UserService userService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // temporarily set an admin Authentication so service-level checks allow initialization
        UsernamePasswordAuthenticationToken initAuth = new UsernamePasswordAuthenticationToken(
            "system-init",
            "",
            java.util.List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(initAuth);

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

        // create default users for authentication
        try {
            User admin = new User(null, "admin", "admin", "ROLE_ADMIN");
            userService.createUser(admin);

            User user = new User(null, "user", "password", "ROLE_USER");
            userService.createUser(user);
        } catch (Exception e) {
            // ignore on re-run
        }

        // clear the temporary authentication
        SecurityContextHolder.clearContext();
    }

}
