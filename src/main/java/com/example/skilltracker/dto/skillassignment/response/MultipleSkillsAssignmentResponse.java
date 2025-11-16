package com.example.skilltracker.dto.skillassignment.response;

import java.util.List;

public record MultipleSkillsAssignmentResponse(
        List<SkillAssignmentResponse> skillAssignmentResponseList
) {}
