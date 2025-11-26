package edu.dhbw.student_management.service;

import edu.dhbw.student_management.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room findById(Long id);

    Room create(Room roomDetails);

    Room update(Long id, Room roomDetails);

    void delete(Long id);
}
