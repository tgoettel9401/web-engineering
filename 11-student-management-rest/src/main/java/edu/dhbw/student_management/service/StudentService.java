package edu.dhbw.student_management.service;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import edu.dhbw.student_management.entity.Student;

@Service
public class StudentService {

    private final Student student;
    private final List<Student> students;

    public StudentService(Student student, List<Student> students) {
        this.student = student;
        this.students = students;
    }

    /**
     * Ruft eine Liste aller registrierten Studenten ab.
     *
     * @return Eine Liste aller Studenten.
     */
    public List<Student> getAllStudents() {
        // Hier greifen wir später auf die Datenbank zu und geben alle Studenten zurück
        return students;
    }

    /**
     * Erstellt einen neuen Studenten.
     * 
     * @param student: Die Struktur des Studenten, der angelegt werden soll
     * @return Student
     */
    public Student createStudent(Student student) {
        // Hier erstellen wir später den Studenten in der Datenbank
        return student;
    }

    public Student getStudentById(Long id) {
        return student;
    }

    /**
     * Aktualisiert einen bestehenden Studenten-Datensatz.
     * * @param id: Die ID des zu aktualisierenden Studenten
     * * @param studentDetails: Die neuen Daten des Studenten
     */
    public Student updateStudent(Long id, Student studentDetails) {
        // Hier greifen wir später auf die Datenbank zu und aktualisieren den Studenten
        return student;
    }

    /**
     * Löscht einen Studenten anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Studenten.
     */
    public ResponseEntity<String> deleteStudent(Long id) {
        // Hier greifen wir später auf die Datenbank zu und löschen den Studenten
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }

}