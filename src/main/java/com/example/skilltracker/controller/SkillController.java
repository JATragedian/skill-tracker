package com.example.skilltracker.controller;

import com.example.skilltracker.dto.skill.request.CreateSkillRequest;
import com.example.skilltracker.dto.skill.response.SkillResponse;
import com.example.skilltracker.service.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/skills")
@SecurityRequirement(name = "bearerAuth")
public class SkillController {

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<SkillResponse> findAll() {
        return service.findAll().stream()
                .map(skill -> new SkillResponse(skill.getId(), skill.getName(), skill.getLevel()))
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public SkillResponse find(@PathVariable Long id) {
        var skill = service.findById(id);
        return new SkillResponse(skill.getId(), skill.getName(), skill.getLevel());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public SkillResponse create(@Valid @RequestBody CreateSkillRequest request) {
        var skill = service.create(request.name(), request.level());
        return new SkillResponse(skill.getId(), skill.getName(), skill.getLevel());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted skill " + id;
    }
}
