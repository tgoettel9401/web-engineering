package edu.dhbw.student_management.controller;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dhbw.student_management.entity.Student;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final Student student;
    private final List<Student> students;

    public StudentController(Student student, List<Student> students) {
        this.student = student;
        this.students = students;
    }

    /**
     * Erstellt einen neuen Studenten.
     *
     * @param student Das im Request-Body übermittelte Studenten-Objekt.
     * @return Das erstellte Studenten-Objekt (inkl. der zugewiesenen ID).
     */
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        // Hier erstellen wir später den Studenten in der Datenbank
        return student;
    }

    /**
     * Ruft einen Studenten anhand seiner eindeutigen ID ab.
     *
     * @param id Die ID des abzurufenden Studenten, übergeben als Pfadvariable.
     * @return Der gefundene Student (oder null/eine Exception, wenn nicht
     *         gefunden).
     */
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        // Hier greifen wir später auf die Datenbank zu und laden den Studenten aus der
        // Datenbank
        return student;
    }

    /**
     * Aktualisiert einen bestehenden Studenten-Datensatz.
     *
     * @param id             Die ID des zu aktualisierenden Studenten.
     * @param studentDetails Das aktualisierte Studenten-Objekt aus dem
     *                       Request-Body.
     * @return Das vollständig aktualisierte Studenten-Objekt.
     */
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        // Hier greifen wir später auf die Datenbank zu und aktualisieren den Studenten
        return student;
    }

    /**
     * Löscht einen Studenten anhand seiner ID.
     * Endpunkt: DELETE /api/v1/students/{id}
     *
     * @param id Die ID des zu löschenden Studenten.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        // Hier greifen wir später auf die Datenbank zu und löschen den Studenten
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }

    /**
     * Ruft eine Liste aller registrierten Studenten ab.
     * Endpunkt: GET /api/v1/students
     *
     * @return Eine Liste aller Studenten.
     */
    @GetMapping
    public List<Student> getAllStudents() {
        // Hier greifen wir später auf die Datenbank zu und geben alle Studenten zurück
        return students;
    }

}