package edu.dhbw.student_management.controller;

import edu.dhbw.student_management.entity.Course;
import edu.dhbw.student_management.service.CourseService;
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
@RequestMapping("/api/v2/courses")
@Tag(name = "Courses", description = "Operations for managing courses (v2)")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Returns a list of all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of courses", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Course.class))))
    })
    public List<Course> list() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Returns a single course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    public ResponseEntity<Course> get(
            @Parameter(description = "ID of the course", example = "1", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Creates a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Course> create(@RequestBody Course course) {
        Course saved = courseService.create(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing course", description = "Updates course data for the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    public ResponseEntity<Course> update(
            @Parameter(description = "ID of the course", example = "1", required = true) @PathVariable Long id,
            @RequestBody Course course) {
        Course updated = courseService.update(id, course);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", description = "Deletes the course with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the course", example = "1", required = true) @PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
