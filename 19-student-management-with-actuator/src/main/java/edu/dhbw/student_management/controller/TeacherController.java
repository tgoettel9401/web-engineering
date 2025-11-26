package edu.dhbw.student_management.controller;

import edu.dhbw.student_management.entity.Teacher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/v2/teachers")
@Tag(name = "Teachers", description = "Operations for managing teachers (v2)")
public class TeacherController {
    private final edu.dhbw.student_management.service.TeacherService teacherService;

    public TeacherController(edu.dhbw.student_management.service.TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Get all teachers", description = "Returns a list of all teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of teachers", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Teacher.class))))
    })
    public List<Teacher> list() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID", description = "Returns a single teacher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teacher.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    public ResponseEntity<Teacher> get(
            @Parameter(description = "ID of the teacher", example = "1", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new teacher", description = "Creates a new teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teacher created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teacher.class)))
    })
    public ResponseEntity<Teacher> create(@RequestBody Teacher teacher) {
        Teacher saved = teacherService.create(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing teacher", description = "Updates teacher data for the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teacher.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    public ResponseEntity<Teacher> update(
            @Parameter(description = "ID of the teacher", example = "1", required = true) @PathVariable Long id,
            @RequestBody Teacher teacher) {
        Teacher saved = teacherService.update(id, teacher);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teacher", description = "Deletes the teacher with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Teacher deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the teacher", example = "1", required = true) @PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().<Void>build();
    }
}
