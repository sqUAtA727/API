package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long age;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Classes getClassroom() {
        return classroom;
    }

    public void setClassroom(Classes classroom) {
        this.classroom = classroom;
    }
}
