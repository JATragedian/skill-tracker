package com.example.skilltracker.entity.exception;

import com.example.skilltracker.entity.AbstractEntity;

public class EntityNotFoundException extends RuntimeException {

    private final Long id;

    public EntityNotFoundException(Class<? extends AbstractEntity> clazz, Long id) {
        super(String.format("Object of class '%s' not found with ID '%d'", clazz.getSimpleName(), id));
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
