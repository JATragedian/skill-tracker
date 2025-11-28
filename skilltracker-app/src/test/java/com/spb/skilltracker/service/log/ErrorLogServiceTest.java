package com.spb.skilltracker.service.log;

import com.spb.skilltracker.entity.ErrorLogEntity;
import com.spb.skilltracker.repository.log.ErrorLogRepository;
import com.spb.skilltracker.service.util.TimeService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ErrorLogServiceTest {

    @Mock
    private ErrorLogRepository errorLogRepository;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private ErrorLogService errorLogService;

    @Test
    void logFailure_should_persist_error_with_timestamp() {

        // Given
        Instant now = Instant.parse("2024-01-01T12:00:00Z");
        when(timeService.nowInstantUTC()).thenReturn(now);

        // When
        errorLogService.logFailure(10L, 20L, "Failed to assign");

        // Then
        ArgumentCaptor<ErrorLogEntity> captor = ArgumentCaptor.forClass(ErrorLogEntity.class);
        verify(errorLogRepository).save(captor.capture());

        ErrorLogEntity saved = captor.getValue();
        assertAll(
                () -> assertEquals(10L, saved.getUserId(), "User id should match"),
                () -> assertEquals(20L, saved.getSkillId(), "Skill id should match"),
                () -> assertEquals("Failed to assign", saved.getReason(), "Reason should match"),
                () -> assertEquals(now, saved.getTimestamp(), "Timestamp should use TimeService")
        );
    }
}
