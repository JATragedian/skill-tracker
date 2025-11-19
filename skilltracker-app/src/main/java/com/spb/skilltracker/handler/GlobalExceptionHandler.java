package com.spb.skilltracker.handler;

import com.spb.skilltracker.entity.exception.DuplicateEntityException;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.service.util.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@AllArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final TimeService timeService;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSkillNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "id", ex.getId(),
                        "timestamp", timeService.nowLocalDateTime()
                ));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateEntityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "timestamp", timeService.nowLocalDateTime()
                ));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AuthorizationDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "timestamp", timeService.nowLocalDateTime()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "message", ex.getMessage(),
                        "timestamp", timeService.nowLocalDateTime()
                ));
    }
}
