package com.example.skilltracker.repository.log;

import com.example.skilltracker.entity.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {

    @Modifying
    @Query("""
       DELETE FROM ErrorLogEntity e
       WHERE e.timestamp < :threshold
       """)
    void deleteOlderThan(Instant threshold);
}
