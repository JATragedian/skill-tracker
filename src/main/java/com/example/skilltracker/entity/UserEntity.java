package com.example.skilltracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity extends AbstractEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    public UserEntity() {
    }

    public UserEntity(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
