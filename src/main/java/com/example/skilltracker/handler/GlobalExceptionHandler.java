package com.example.skilltracker.handler;

import com.example.skilltracker.dto.skill.exception.SkillNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSkillNotFound(SkillNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "id", ex.getId(),
                        "timestamp", LocalDateTime.now()
                ));
    }
}
