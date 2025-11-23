package edu.dhbw.student_management.entity;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String matrikelnummer;
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

    // Default constructor needed for JSON deserialization in tests and controllers
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
    }

    public void removeCourse(Course course) {
        this.enrolledCourses.remove(course);
    }
}