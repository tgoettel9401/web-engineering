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
import edu.dhbw.student_management.service.StudentService;

@RestController
@RequestMapping("/api/v2/students")
@Tag(name = "Students", description = "Operations for managing students (v2)")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // --- Get all students ---
    @GetMapping
    @Operation(summary = "Get all students", description = "Returns a list of all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of students", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    })
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // --- Get student by id ---
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Returns a single student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public Student getStudentById(
            @Parameter(description = "ID of the student", example = "1", required = true) @PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // --- Create student ---
    @PostMapping
    @Operation(summary = "Create a new student", description = "Creates a new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class)))
    })
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    // --- Update student ---
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing student", description = "Updates student data for the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentService.updateStudent(id, studentDetails);
    }

    // --- Delete student ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Deletes the student with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
