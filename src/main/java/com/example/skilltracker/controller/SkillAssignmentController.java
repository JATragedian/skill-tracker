package com.example.skilltracker.controller;

import com.example.skilltracker.dto.skillassignment.request.AssignMultipleSkillsRequest;
import com.example.skilltracker.dto.skillassignment.request.CreateSkillAssignmentRequest;
import com.example.skilltracker.dto.skillassignment.response.MultipleSkillsAssignmentResponse;
import com.example.skilltracker.dto.skillassignment.response.SkillAssignmentResponse;
import com.example.skilltracker.entity.SkillAssignmentEntity;
import com.example.skilltracker.service.SkillAssignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/assignments")
@SecurityRequirement(name = "bearerAuth")
public class SkillAssignmentController {

    private final SkillAssignmentService service;

    public SkillAssignmentController(SkillAssignmentService service) {
        this.service = service;
    }

    @PostMapping
    public SkillAssignmentResponse assignSkill(@RequestBody CreateSkillAssignmentRequest request) {
        SkillAssignmentEntity assignment =
                service.assignSkillToUser(request.userId(), request.skillId(), request.proficiency());

        return new SkillAssignmentResponse(
                assignment.getId(),
                assignment.getUser().getId(),
                assignment.getSkill().getId(),
                assignment.getProficiency()
        );
    }

    @PostMapping("/multiple")
    public MultipleSkillsAssignmentResponse assignMultiple(@RequestBody AssignMultipleSkillsRequest request) {
        List<SkillAssignmentResponse> responseList = service.assignMultiple(
                        request.userId(),
                        request.skillIds(),
                        request.proficiency()
                ).stream()
                .map(entity -> new SkillAssignmentResponse(
                        entity.getId(),
                        entity.getUser().getId(),
                        entity.getSkill().getId(),
                        entity.getProficiency())
                )
                .toList();

        return new MultipleSkillsAssignmentResponse(responseList);
    }
}