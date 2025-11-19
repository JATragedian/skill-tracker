package com.spb.skilltracker.controller;

import com.spb.skilltracker.dto.category.request.CreateCategoryRequest;
import com.spb.skilltracker.dto.category.response.CategoryResponse;
import com.spb.skilltracker.mapper.CategoryMapper;
import com.spb.skilltracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    public List<CategoryResponse> findAll() {
        return mapper.toResponseList(service.findAll());
    }

    @GetMapping("/{id}")
    public CategoryResponse find(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return mapper.toResponse(service.create(request.name()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted category " + id;
    }
}
