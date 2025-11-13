package com.example.skilltracker.dto.skill.exception;

public class SkillNotFoundException extends RuntimeException {

    private final Long id;

    public SkillNotFoundException(Long id) {
        super("Skill not found with ID: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
