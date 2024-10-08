package com.example.cielobackend.service;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.CategoryDto;
import com.example.cielobackend.dto.CategoryDtoResponse;

import java.util.List;

public interface CategoryService {
   CategoryDtoResponse getCategoryById(long id);
   List<CategoryDtoResponse> getCategories();
   CategoryDtoResponse addCategory(CategoryDto categoryDto);
   CategoryDtoResponse renameCategory(long id, String name);
   CategoryDtoResponse updateCategory(long id, CategoryDto categoryDto);
   void deleteCategory(long id);
   void setCategoryImageName(long id, String imageName);
   List<AttributeDtoResponse> getAllAttributesByCategoryId(long id);
   AttributeDtoResponse addAttributeToCategory(long categoryId, long attributeId);
   void removeAttributeFromCategory(long categoryId, long attributeId);
   Long countAllListingsByCategory(long id);
}
