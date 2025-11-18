package com.example.skilltracker.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimeService {

    @Value("${app.time.zone}")
    private String timezone;

    public LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of(timezone));
    }
}
