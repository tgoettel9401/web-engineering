package edu.dhbw.student_management.controller;

import edu.dhbw.student_management.entity.Room;

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
@RequestMapping("/api/v2/rooms")
@Tag(name = "Rooms", description = "Operations for managing rooms (v2)")
public class RoomController {
    private final edu.dhbw.student_management.service.RoomService roomService;

    public RoomController(edu.dhbw.student_management.service.RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "Get all rooms", description = "Returns a list of all rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of rooms", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Room.class))))
    })
    public List<Room> list() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID", description = "Returns a single room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)
    })
    public ResponseEntity<Room> get(
            @Parameter(description = "ID of the room", example = "1", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new room", description = "Creates a new room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)))
    })
    public ResponseEntity<Room> create(@RequestBody Room room) {
        Room saved = roomService.create(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing room", description = "Updates room data for the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)
    })
    public ResponseEntity<Room> update(
            @Parameter(description = "ID of the room", example = "1", required = true) @PathVariable Long id,
            @RequestBody Room room) {
        Room saved = roomService.update(id, room);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a room", description = "Deletes the room with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the room", example = "1", required = true) @PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().<Void>build();
    }
}
