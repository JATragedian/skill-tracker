package com.spb.skilltracker.service.log;

import com.spb.skilltracker.entity.ErrorLogEntity;
import com.spb.skilltracker.repository.log.ErrorLogRepository;
import com.spb.skilltracker.service.util.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ErrorLogService {

    private final ErrorLogRepository repository;
    private final TimeService timeService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logFailure(Long userId, Long skillId, String reason) {
        repository.save(new ErrorLogEntity(userId, skillId, reason, timeService.nowInstantUTC()));
    }
}
