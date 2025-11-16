package com.example.skilltracker.service;

import com.example.skilltracker.repository.ErrorLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class ErrorLogCleanupService {

    private final ErrorLogRepository errorLogRepository;

    public ErrorLogCleanupService(ErrorLogRepository repository) {
        this.errorLogRepository = repository;
    }

    // Every day at 4 a.m.
    @Transactional
    @Scheduled(cron = "0 0 4 * * *")
    public void cleanupOldLogs() {
        errorLogRepository.deleteOlderThan(Instant.now().minus(7, ChronoUnit.DAYS));
    }
}
