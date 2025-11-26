package edu.dhbw.student_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import edu.dhbw.student_management.entity.Room;
import edu.dhbw.student_management.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllReturnsList() {
        Room r1 = new Room(1L, "R1", "L1", 10);
        Room r2 = new Room(2L, "R2", "L2", 20);
        when(roomRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Room> res = roomService.findAll();
        assertEquals(2, res.size());
    }

    @Test
    void findByIdFound() {
        Room r = new Room(1L, "Room 101", "Building A", 30);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(r));

        Room found = roomService.findById(1L);
        assertEquals("Room 101", found.getName());
    }

    @Test
    void findByIdNotFoundThrows() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> roomService.findById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void createRoomSaves() {
        Room r = new Room(null, "Room 102", "Building B", 50);
        Room saved = new Room(10L, "Room 102", "Building B", 50);
        when(roomRepository.save(r)).thenReturn(saved);

        Room result = roomService.create(r);
        assertEquals(10L, result.getId());
    }

    @Test
    void updateRoomUpdates() {
        Room existing = new Room(1L, "Old Room", "Old Loc", 10);
        Room update = new Room(null, "New Room", "New Loc", 20);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));

        Room res = roomService.update(1L, update);
        assertEquals("New Room", res.getName());
        assertEquals(20, res.getCapacity());
    }

    @Test
    void updateRoomNotFoundThrows() {
        Room update = new Room(null, "New Room", "New Loc", 20);
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> roomService.update(99L, update));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteRoomDeletes() {
        Room existing = new Room(1L, "To Delete", "Loc", 5);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(existing));

        roomService.delete(1L);
        verify(roomRepository).delete(existing);
    }

    @Test
    void deleteRoomNotFoundThrows() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> roomService.delete(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
