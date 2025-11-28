package com.spb.skilltracker.service.log;

import com.spb.skilltracker.repository.log.ErrorLogRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ErrorLogCleanupServiceTest {

    @Mock
    private ErrorLogRepository errorLogRepository;

    @InjectMocks
    private ErrorLogCleanupService cleanupService;

    @Test
    void cleanupOldLogs_should_delete_logs_before_threshold() {

        // Given
        ReflectionTestUtils.setField(cleanupService, "daysToKeep", 3L);

        // When
        cleanupService.cleanupOldLogs();

        // Then
        ArgumentCaptor<Instant> captor = ArgumentCaptor.forClass(Instant.class);
        verify(errorLogRepository).deleteOlderThan(captor.capture());

        Instant threshold = captor.getValue();
        Instant expectedMax = Instant.now();
        Instant expectedMin = expectedMax.minusSeconds(3 * 24 * 3600L + 5);

        assertTrue(threshold.isBefore(expectedMax), "Threshold should be before now");
        assertTrue(threshold.isAfter(expectedMin), "Threshold should use configured days");
    }
}
