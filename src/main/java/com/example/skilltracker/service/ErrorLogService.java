package com.example.skilltracker.service;

import com.example.skilltracker.entity.ErrorLogEntity;
import com.example.skilltracker.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErrorLogService {

    private final ErrorLogRepository repository;

    public ErrorLogService(ErrorLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logFailure(Long userId, Long skillId, String reason) {
        repository.save(new ErrorLogEntity(userId, skillId, reason));
    }
}
