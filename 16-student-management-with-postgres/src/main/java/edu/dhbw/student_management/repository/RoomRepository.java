package edu.dhbw.student_management.repository;

import edu.dhbw.student_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
