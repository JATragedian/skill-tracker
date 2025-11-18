package com.example.skilltracker.controller;

import com.example.skilltracker.dto.skill.request.CreateSkillRequest;
import com.example.skilltracker.dto.skill.response.SkillResponse;
import com.example.skilltracker.mapper.SkillMapper;
import com.example.skilltracker.service.SkillService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService service;
    private final SkillMapper mapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<SkillResponse> findAll() {
        return mapper.toResponseList(service.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public SkillResponse find(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SkillResponse create(@Valid @RequestBody CreateSkillRequest request) {
        return mapper.toResponse(service.create(request.name(), request.level(), request.categoryId()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted skill " + id;
    }
}
