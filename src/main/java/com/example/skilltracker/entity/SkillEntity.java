package com.example.skilltracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skill")
public class SkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int level;

    public SkillEntity() {
    }

    public SkillEntity(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
