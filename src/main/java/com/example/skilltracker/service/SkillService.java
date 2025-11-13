package com.example.skilltracker.service;

import com.example.skilltracker.dto.skill.exception.SkillNotFoundException;
import com.example.skilltracker.model.Skill;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SkillService {

    private final Map<Long, Skill> skills = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Skill> findAll() {
        return new ArrayList<>(skills.values());
    }

    public Skill findById(Long id) {
        Skill skill = skills.get(id);
        if (skill == null) {
            throw new SkillNotFoundException(id);
        }

        return skill;
    }

    public Skill create(String name, int level) {
        Long id = counter.getAndIncrement();
        Skill skill = new Skill(id, name, level);
        skills.put(id, skill);
        return skill;
    }

    public boolean delete(Long id) {
        return skills.remove(id) != null;
    }
}
