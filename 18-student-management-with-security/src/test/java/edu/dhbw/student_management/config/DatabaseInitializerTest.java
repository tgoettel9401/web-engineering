package edu.dhbw.student_management.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.entity.Room;
import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.entity.Teacher;
import edu.dhbw.student_management.service.CourseService;
import edu.dhbw.student_management.service.RoomService;
import edu.dhbw.student_management.service.StudentService;
import edu.dhbw.student_management.service.TeacherService;
import edu.dhbw.student_management.service.UserService;

class DatabaseInitializerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private RoomService roomService;

    private UserService userService;

    private DatabaseInitializer initializer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initializer = new DatabaseInitializer(studentService, courseService, teacherService, roomService, userService);
    }

    @Test
    void runCreatesEntitiesAndRelationships() throws Exception {
        // Mock Teachers
        Teacher t1 = new Teacher(1L, "Anna", "Lehmann", "EMP-001");
        Teacher t2 = new Teacher(2L, "Boris", "Keller", "EMP-002");
        when(teacherService.create(any(Teacher.class))).thenReturn(t1, t2);

        // Mock Rooms
        Room r1 = new Room(1L, "R101", "Building A", 30);
        Room r2 = new Room(2L, "R202", "Building B", 25);
        when(roomService.create(any(Room.class))).thenReturn(r1, r2);

        // Mock Courses
        Course c1 = new Course(1L, "Web-Engineering");
        Course c2 = new Course(2L, "Algorithmen");
        when(courseService.create(any(Course.class))).thenReturn(c1, c2);

        // Mock Students
        when(studentService.createStudent(any(Student.class))).thenReturn(new Student(1L, "S", "T", "m1"));

        // Execute
        initializer.run();

        // Verify interactions
        verify(teacherService, times(2)).create(any(Teacher.class));
        verify(roomService, times(2)).create(any(Room.class));

        // Verify courses are created with relationships (implicitly tested by the flow,
        // but we can verify calls)
        verify(courseService, times(2)).create(any(Course.class));

        // Verify students are created
        verify(studentService, times(3)).createStudent(any(Student.class));
    }
}
