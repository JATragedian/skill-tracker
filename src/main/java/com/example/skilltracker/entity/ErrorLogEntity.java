package com.example.skilltracker.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "error_logs")
public class ErrorLogEntity extends AbstractEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long skillId;

    @Column(nullable = false, length = 500)
    private String reason;

    @Column(nullable = false)
    private Instant timestamp;

    public ErrorLogEntity() {}

    public ErrorLogEntity(Long userId, Long skillId, String reason) {
        this.userId = userId;
        this.skillId = skillId;
        this.reason = reason;
        this.timestamp = Instant.now();
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public String getReason() {
        return reason;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
