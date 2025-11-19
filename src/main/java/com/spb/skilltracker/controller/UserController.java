package com.spb.skilltracker.controller;

import com.spb.skilltracker.dto.auth.response.UserResponse;
import com.spb.skilltracker.entity.user.UserEntity;
import com.spb.skilltracker.mapper.UserMapper;
import com.spb.skilltracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponse getCurrentUser(Authentication authentication) {
        return mapper.toResponse((UserEntity) authentication.getPrincipal());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> findAll() {
        return mapper.toResponseList(service.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse find(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted user " + id;
    }
}
