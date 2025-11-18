package com.example.skilltracker.controller;

import com.example.skilltracker.dto.skillassignment.request.AssignMultipleSkillsRequest;
import com.example.skilltracker.dto.skillassignment.request.CreateSkillAssignmentRequest;
import com.example.skilltracker.dto.skillassignment.response.MultipleSkillsAssignmentResponse;
import com.example.skilltracker.dto.skillassignment.response.SkillAssignmentResponse;
import com.example.skilltracker.entity.SkillAssignmentEntity;
import com.example.skilltracker.entity.user.Role;
import com.example.skilltracker.entity.user.UserEntity;
import com.example.skilltracker.service.SkillAssignmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/assignments")
public class SkillAssignmentController {

    private final SkillAssignmentService service;

    public SkillAssignmentController(SkillAssignmentService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public MultipleSkillsAssignmentResponse getMyAssignments(Authentication authentication) {
        var currentUser = (UserEntity) authentication.getPrincipal();

        List<SkillAssignmentEntity> list =
                currentUser.getRole() == Role.ADMIN
                        ? service.findAll()
                        : service.findByUserId(currentUser.getId());

        return new MultipleSkillsAssignmentResponse(
                list.stream()
                        .map(a -> new SkillAssignmentResponse(
                                a.getId(),
                                a.getUser().getId(),
                                a.getSkill().getId(),
                                a.getProficiency()
                        ))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ac.canAccessAssignment(#id, authentication.name)")
    public SkillAssignmentResponse getById(@PathVariable Long id) {
        var skillAssignmentEntity = service.findById(id);

        return new SkillAssignmentResponse(
                skillAssignmentEntity.getId(),
                skillAssignmentEntity.getUser().getId(),
                skillAssignmentEntity.getSkill().getId(),
                skillAssignmentEntity.getProficiency()
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public SkillAssignmentResponse assignSkill(
            @RequestBody CreateSkillAssignmentRequest request,
            Authentication authentication
    ) {
        var currentUser = (UserEntity) authentication.getPrincipal();
        var userId = currentUser.getRole() == Role.ADMIN ? request.userId() : currentUser.getId();

        SkillAssignmentEntity assignment =
                service.assignSkillToUser(userId, request.skillId(), request.proficiency());

        return new SkillAssignmentResponse(
                assignment.getId(),
                assignment.getUser().getId(),
                assignment.getSkill().getId(),
                assignment.getProficiency()
        );
    }

    @PostMapping("/multiple")
    @PreAuthorize("isAuthenticated()")
    public MultipleSkillsAssignmentResponse assignMultiple(
            @RequestBody AssignMultipleSkillsRequest request,
            Authentication authentication
    ) {
        var currentUser = (UserEntity) authentication.getPrincipal();
        var userId = currentUser.getRole() == Role.ADMIN ? request.userId() : currentUser.getId();

        List<SkillAssignmentResponse> responseList = service.assignMultiple(
                        userId,
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ac.canAccessAssignment(#id, authentication.name)")
    public SkillAssignmentResponse update(
            @PathVariable Long id,
            @RequestBody CreateSkillAssignmentRequest request
    ) {
        var updated = service.update(id, request.userId(), request.skillId(), request.proficiency());

        return new SkillAssignmentResponse(
                updated.getId(),
                updated.getUser().getId(),
                updated.getSkill().getId(),
                updated.getProficiency()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ac.canAccessAssignment(#id, authentication.name)")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}