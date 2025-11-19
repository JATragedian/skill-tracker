package com.spb.skilltracker.entity.exception;

import com.spb.skilltracker.entity.AbstractEntity;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final Long id;

    public EntityNotFoundException(Class<? extends AbstractEntity> clazz, Long id) {
        super(String.format("Object of class '%s' not found with ID '%d'", clazz.getSimpleName(), id));
        this.id = id;
    }
}
