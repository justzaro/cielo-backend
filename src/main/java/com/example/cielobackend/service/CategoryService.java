package com.example.cielobackend.service;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.CategoryDto;
import com.example.cielobackend.dto.CategoryDtoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
   CategoryDtoResponse getCategoryById(long id);
   List<CategoryDtoResponse> getCategories();
   CategoryDtoResponse addCategory(CategoryDto categoryDto);
   CategoryDtoResponse renameCategory(long id, String name);
   CategoryDtoResponse updateCategory(long id, CategoryDto categoryDto);
   void deleteCategory(long id);
   void setCategoryImageName(long id, String imageName);
   AttributeDtoResponse addAttribute(long categoryId, long attributeId);
}
