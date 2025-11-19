package com.spb.skilltracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
