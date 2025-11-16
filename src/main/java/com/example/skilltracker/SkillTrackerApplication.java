package com.example.skilltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SkillTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillTrackerApplication.class, args);
    }
}
