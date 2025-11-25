package edu.dhbw.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String matrikelnummer;

    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> enrolledCourses = new ArrayList<>();

    /**
     * Konstruktor zur Initialisierung aller Kernfelder des Studenten-Datensatzes.
     * Die Liste der Kurse wird standardmäßig als leere ArrayList initialisiert.
     *
     * @param id             Die eindeutige ID des Studenten.
     * @param firstName      Der Vorname des Studenten.
     * @param lastName       Der Nachname des Studenten.
     * @param matrikelnummer Die Matrikelnummer des Studenten.
     */
    public Student(Long id, String firstName, String lastName, String matrikelnummer) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.matrikelnummer = matrikelnummer;
    }

    // Default constructor needed for JPA / JSON deserialization in tests and
    // controllers
    public Student() {
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMatrikelnummer() {
        return this.matrikelnummer;
    }

    public List<Course> getEnrolledCourses() {
        return this.enrolledCourses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public void addCourse(Course course) {
        this.enrolledCourses.add(course);
        if (!course.getStudents().contains(this)) {
            course.getStudents().add(this);
        }
    }

    public void removeCourse(Course course) {
        this.enrolledCourses.remove(course);
        course.getStudents().remove(this);
    }
}