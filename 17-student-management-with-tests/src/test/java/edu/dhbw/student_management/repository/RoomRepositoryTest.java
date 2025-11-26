package edu.dhbw.student_management.repository;

import edu.dhbw.student_management.entity.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void uniqueRoomNameConstraint() {
        Room r1 = new Room(null, "H101", "Gebäude A", 30);
        roomRepository.saveAndFlush(r1);

        Room r2 = new Room(null, "H101", "Gebäude B", 40);
        assertThrows(DataIntegrityViolationException.class, () -> roomRepository.saveAndFlush(r2));
    }
}
