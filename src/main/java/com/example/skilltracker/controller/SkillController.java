package com.example.skilltracker.controller;

import com.example.skilltracker.dto.skill.request.CreateSkillRequest;
import com.example.skilltracker.dto.skill.response.SkillResponse;
import com.example.skilltracker.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<SkillResponse> getAllSkills() {
        return skillService.findAll().stream()
                .map(skill -> new SkillResponse(skill.getId(), skill.getName(), skill.getLevel()))
                .toList();
    }

    @GetMapping("/{id}")
    public SkillResponse getSkill(@PathVariable Long id) {
        var skill = skillService.findById(id);
        return new SkillResponse(skill.getId(), skill.getName(), skill.getLevel());
    }

    @PostMapping
    public SkillResponse addSkill(@Valid @RequestBody CreateSkillRequest request) {
        var skill = skillService.create(request.name(), request.level());
        return new SkillResponse(skill.getId(), skill.getName(), skill.getLevel());
    }

    @DeleteMapping("/{id}")
    public String deleteSkill(@PathVariable Long id) {
        skillService.delete(id);
        return "Deleted skill " + id;
    }
}
