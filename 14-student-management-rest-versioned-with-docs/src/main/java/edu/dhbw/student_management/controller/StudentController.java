package edu.dhbw.student_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import edu.dhbw.student_management.entity.Student;
import edu.dhbw.student_management.service.StudentServiceDelegate;

@RestController
@RequestMapping("/api")
@Tag(name = "Students", description = "Operations for managing students (v1 and v2)")
public class StudentController {

    private final StudentServiceDelegate delegate;

    public StudentController(StudentServiceDelegate delegate) {
        this.delegate = delegate;
    }

    // --- Get all students ---
    @GetMapping("/v1/students")
    @Operation(summary = "Get all students (v1)", description = "Returns a list of all students for v1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of students", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    })
    public List<Student> getAllStudentsV1() {
        return delegate.getForVersion("v1").getAllStudents();
    }

    @GetMapping("/v2/students")
    @Operation(summary = "Get all students (v2)", description = "Returns a list of all students for v2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of students", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    })
    public List<Student> getAllStudentsV2() {
        return delegate.getForVersion("v2").getAllStudents();
    }

    // --- Get student by id ---
    @GetMapping("/v1/students/{id}")
    @Operation(summary = "Get student by ID (v1)", description = "Returns a single student by ID for v1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public Student getStudentByIdV1(
            @Parameter(description = "ID of the student", example = "1", required = true) @PathVariable Long id) {
        return delegate.getForVersion("v1").getStudentById(id);
    }

    @GetMapping("/v2/students/{id}")
    @Operation(summary = "Get student by ID (v2)", description = "Returns a single student by ID for v2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public Student getStudentByIdV2(
            @Parameter(description = "ID of the student", example = "1", required = true) @PathVariable Long id) {
        return delegate.getForVersion("v2").getStudentById(id);
    }

    // --- Create student ---
    @PostMapping("/v1/students")
    @Operation(summary = "Create a new student (v1)", description = "Creates a new student for v1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class)))
    })
    public Student createStudentV1(@RequestBody Student student) {
        return delegate.getForVersion("v1").createStudent(student);
    }

    @PostMapping("/v2/students")
    @Operation(summary = "Create a new student (v2)", description = "Creates a new student for v2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class)))
    })
    public Student createStudentV2(@RequestBody Student student) {
        return delegate.getForVersion("v2").createStudent(student);
    }

    // --- Update student ---
    @PutMapping("/v1/students/{id}")
    @Operation(summary = "Update an existing student (v1)", description = "Updates student data for the specified ID in v1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public Student updateStudentV1(@PathVariable Long id, @RequestBody Student studentDetails) {
        return delegate.getForVersion("v1").updateStudent(id, studentDetails);
    }

    @PutMapping("/v2/students/{id}")
    @Operation(summary = "Update an existing student (v2)", description = "Updates student data for the specified ID in v2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public Student updateStudentV2(@PathVariable Long id, @RequestBody Student studentDetails) {
        return delegate.getForVersion("v2").updateStudent(id, studentDetails);
    }

    // --- Delete student ---
    @DeleteMapping("/v1/students/{id}")
    @Operation(summary = "Delete a student (v1)", description = "Deletes the student with the given ID in v1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public ResponseEntity<String> deleteStudentV1(@PathVariable Long id) {
        return delegate.getForVersion("v1").deleteStudent(id);
    }

    @DeleteMapping("/v2/students/{id}")
    @Operation(summary = "Delete a student (v2)", description = "Deletes the student with the given ID in v2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public ResponseEntity<String> deleteStudentV2(@PathVariable Long id) {
        return delegate.getForVersion("v2").deleteStudent(id);
    }
}
