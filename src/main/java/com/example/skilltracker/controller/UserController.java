package com.example.skilltracker.controller;

import com.example.skilltracker.dto.user.request.CreateUserRequest;
import com.example.skilltracker.dto.user.response.UserResponse;
import com.example.skilltracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return service.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse find(@PathVariable Long id) {
        var user = service.findById(id);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        var user =  service.create(request.username(), request.email());
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted user " + id;
    }
}
