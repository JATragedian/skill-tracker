package com.spb.skilltracker.entity.exception;

import com.spb.skilltracker.entity.AbstractEntity;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(Class<? extends AbstractEntity> clazz) {
        super(String.format("Equal entity of class '%s' already exists", clazz.getSimpleName()));
    }
}
