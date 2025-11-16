package com.example.skilltracker.handler;

import com.example.skilltracker.entity.exception.DuplicateEntityException;
import com.example.skilltracker.entity.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSkillNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "id", ex.getId(),
                        "timestamp", LocalDateTime.now(ZoneId.of("America/Los_Angeles"))
                ));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateEntityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now(ZoneId.of("America/Los_Angeles"))
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now(ZoneId.of("America/Los_Angeles"))
                ));
    }
}
