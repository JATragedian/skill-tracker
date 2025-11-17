package com.example.skilltracker.controller;

import com.example.skilltracker.dto.category.request.CreateCategoryRequest;
import com.example.skilltracker.dto.category.response.CategoryResponse;
import com.example.skilltracker.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return service.findAll().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse find(@PathVariable Long id) {
        var category = service.findById(id);
        return new CategoryResponse(category.getId(), category.getName());
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        var category = service.create(request.name());
        return new CategoryResponse(category.getId(), category.getName());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted category " + id;
    }
}
