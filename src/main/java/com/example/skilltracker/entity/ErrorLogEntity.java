package com.example.skilltracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
