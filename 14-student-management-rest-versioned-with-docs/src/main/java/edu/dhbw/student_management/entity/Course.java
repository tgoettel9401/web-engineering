package edu.dhbw.student_management.entity;

public class Course {
    private Long id;
    private String name;

    /**
     * Erstellt ein neues Course-Objekt.
     * 
     * @param id   Die eindeutige ID des Kurses.
     * @param name Der Name des Kurses.
     */
    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
