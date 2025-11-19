package com.spb.skilltracker.dto.skill.response;

public record SkillResponse(
        Long id,
        String name,
        int level,
        String category
) {}
