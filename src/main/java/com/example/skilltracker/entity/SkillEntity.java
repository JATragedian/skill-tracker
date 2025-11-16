package com.example.skilltracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class SkillEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public SkillEntity() {
    }

    public SkillEntity(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
