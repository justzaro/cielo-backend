package com.example.cielobackend.controller;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.CategoryDto;
import com.example.cielobackend.dto.CategoryDtoResponse;
import com.example.cielobackend.service.CategoryService;
import jakarta.persistence.PostPersist;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.basePath}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDtoResponse getCategoryById(@PathVariable("id") long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<CategoryDtoResponse> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}/listings/count")
    public Long getListingsCountByCategoryId(@PathVariable long id) {
        return categoryService.countAllListingsByCategory(id);
    }

    @PostMapping
    public CategoryDtoResponse addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PostMapping("/{categoryId}/attributes/{attributeId}")
    public AttributeDtoResponse addAttribute(@PathVariable long categoryId,
                                             @PathVariable long attributeId) {
        return categoryService.addAttribute(categoryId, attributeId);
    }

    @PutMapping("/{id}")
    public CategoryDtoResponse updateCategory(@PathVariable("id") long id,
                                              @RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @PatchMapping("/{id}")
    public CategoryDtoResponse renameCategory(@PathVariable("id") long id,
                                              @RequestBody String name) {
        return categoryService.renameCategory(id, name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity
               .status(HttpStatus.NO_CONTENT)
               .build();
    }
}
