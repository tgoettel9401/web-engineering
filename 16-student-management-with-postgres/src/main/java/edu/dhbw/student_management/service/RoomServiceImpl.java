package edu.dhbw.student_management.service;

import edu.dhbw.student_management.entity.Room;
import edu.dhbw.student_management.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Long id, Room roomDetails) {
        Room existing = findById(id);
        existing.setName(roomDetails.getName());
        existing.setLocation(roomDetails.getLocation());
        existing.setCapacity(roomDetails.getCapacity());
        return roomRepository.save(existing);
    }

    public void delete(Long id) {
        Room existing = findById(id);
        roomRepository.delete(existing);
    }
}
