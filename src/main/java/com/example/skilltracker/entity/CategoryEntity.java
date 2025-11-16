package com.example.skilltracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
