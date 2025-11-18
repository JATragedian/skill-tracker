package com.example.skilltracker.service.log;

import com.example.skilltracker.repository.log.ErrorLogRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class ErrorLogCleanupService {

    private final ErrorLogRepository errorLogRepository;

    @Value("${app.db.logs.days-to-keep}")
    private Long daysToKeep;

    // Every day at 4 a.m.
    @Transactional
    @Scheduled(cron = "0 0 4 * * *")
    public void cleanupOldLogs() {
        errorLogRepository.deleteOlderThan(Instant.now().minus(daysToKeep, ChronoUnit.DAYS));
    }
}
