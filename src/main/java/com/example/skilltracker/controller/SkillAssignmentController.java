package com.example.skilltracker.controller;

import com.example.skilltracker.dto.skillassignment.request.AssignMultipleSkillsRequest;
import com.example.skilltracker.dto.skillassignment.request.CreateSkillAssignmentRequest;
import com.example.skilltracker.dto.skillassignment.response.SkillAssignmentResponse;
import com.example.skilltracker.entity.user.Role;
import com.example.skilltracker.entity.user.UserEntity;
import com.example.skilltracker.mapper.SkillAssignmentMapper;
import com.example.skilltracker.service.SkillAssignmentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/assignments")
public class SkillAssignmentController {

    private final SkillAssignmentService service;
    private final SkillAssignmentMapper mapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<SkillAssignmentResponse> getMyAssignments(Authentication authentication) {
        var currentUser = (UserEntity) authentication.getPrincipal();

        return mapper.toResponseList(currentUser.getRole() == Role.ADMIN
                        ? service.findAll()
                        : service.findByUserId(currentUser.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ac.canAccessAssignment(#id, authentication.name)")
    public SkillAssignmentResponse getById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public SkillAssignmentResponse assignSkill(
            @RequestBody CreateSkillAssignmentRequest request,
            Authentication authentication
    ) {
        var currentUser = (UserEntity) authentication.getPrincipal();
        var userId = currentUser.getRole() == Role.ADMIN ? request.userId() : currentUser.getId();

        return mapper.toResponse(service.assignSkillToUser(userId, request.skillId(), request.proficiency()));
    }

    @PostMapping("/multiple")
    @PreAuthorize("isAuthenticated()")
    public List<SkillAssignmentResponse> assignMultiple(
            @RequestBody AssignMultipleSkillsRequest request,
            Authentication authentication
    ) {
        var currentUser = (UserEntity) authentication.getPrincipal();
        var userId = currentUser.getRole() == Role.ADMIN ? request.userId() : currentUser.getId();

        return mapper.toResponseList(service.assignMultiple(userId, request.skillIds(), request.proficiency()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ac.canAccessAssignment(#id, authentication.name)")
    public SkillAssignmentResponse update(
            @PathVariable Long id,
            @RequestBody CreateSkillAssignmentRequest request
    ) {
        return mapper.toResponse(service.update(id, request.userId(), request.skillId(), request.proficiency()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ac.canAccessAssignment(#id, authentication.name)")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}