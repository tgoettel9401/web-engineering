package edu.dhbw.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @SequenceGenerator(name = "room_seq", sequenceName = "room_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private Integer capacity;

    @OneToMany(mappedBy = "room")
    private List<Course> courses = new ArrayList<>();

    public Room() {
    }

    public Room(Long id, String name, String location, Integer capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        if (course.getRoom() != this) {
            course.setRoom(this);
        }
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        if (course.getRoom() == this) {
            course.setRoom(null);
        }
    }
}
