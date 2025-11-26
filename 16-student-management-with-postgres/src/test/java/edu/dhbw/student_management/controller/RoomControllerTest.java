package edu.dhbw.student_management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import edu.dhbw.student_management.entity.Room;
import edu.dhbw.student_management.service.RoomService;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @Test
    void getAllRooms_ShouldReturnList() throws Exception {
        Room r = new Room(1L, "Room 101", "Building A", 30);
        when(roomService.findAll()).thenReturn(Arrays.asList(r));

        mockMvc.perform(get("/api/v2/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Room 101"))
                .andExpect(jsonPath("$[0].location").value("Building A"))
                .andExpect(jsonPath("$[0].capacity").value(30));
    }

    @Test
    void getAllRooms_ShouldReturnEmptyList() throws Exception {
        when(roomService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getRoomById_ShouldReturnRoom_WhenFound() throws Exception {
        Room r = new Room(1L, "Room 101", "Building A", 30);
        when(roomService.findById(1L)).thenReturn(r);

        mockMvc.perform(get("/api/v2/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Room 101"));
    }

    @Test
    void getRoomById_ShouldReturn404_WhenNotFound() throws Exception {
        when(roomService.findById(99L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v2/rooms/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRoom_ShouldReturnCreatedRoom() throws Exception {
        Room saved = new Room(2L, "Room 102", "Building B", 50);
        when(roomService.create(any(Room.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v2/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Room 102\",\"location\":\"Building B\",\"capacity\":50}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Room 102"));
    }

    @Test
    void updateRoom_ShouldReturnUpdatedRoom_WhenFound() throws Exception {
        Room updated = new Room(1L, "Room 101", "Building A", 35);
        when(roomService.update(eq(1L), any(Room.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v2/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Room 101\",\"location\":\"Building A\",\"capacity\":35}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity").value(35));
    }

    @Test
    void updateRoom_ShouldReturn404_WhenNotFound() throws Exception {
        when(roomService.update(eq(99L), any(Room.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/api/v2/rooms/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Ghost Room\",\"location\":\"Nowhere\",\"capacity\":0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoom_ShouldReturnNoContent_WhenDeleted() throws Exception {
        mockMvc.perform(delete("/api/v2/rooms/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRoom_ShouldReturnNotFound_WhenNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(roomService).delete(99L);

        mockMvc.perform(delete("/api/v2/rooms/99"))
                .andExpect(status().isNotFound());
    }
}
