package com.example.skilltracker.entity;

import com.example.skilltracker.entity.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "skill_assignments")
public class SkillAssignmentEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id")
    private SkillEntity skill;

    @Column(nullable = false)
    private int proficiency;

    public SkillAssignmentEntity() {}

    public SkillAssignmentEntity(UserEntity user, SkillEntity skill, int proficiency) {
        this.user = user;
        this.skill = skill;
        this.proficiency = proficiency;
    }

    public UserEntity getUser() {
        return user;
    }

    public SkillEntity getSkill() {
        return skill;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setSkill(SkillEntity skill) {
        this.skill = skill;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }
}
