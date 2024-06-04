package com.example.cielobackend.service.implementation;

import com.dropbox.core.DbxException;
import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.CategoryDto;
import com.example.cielobackend.dto.CategoryDtoResponse;
import com.example.cielobackend.exception.ResourceAlreadyExistsException;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.Attribute;
import com.example.cielobackend.model.Category;
import com.example.cielobackend.repository.AttributeRepository;
import com.example.cielobackend.repository.CategoryRepository;
import com.example.cielobackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDtoResponse getCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        return modelMapper.map(category, CategoryDtoResponse.class);
    }

    public List<CategoryDtoResponse> getCategories() {
        return categoryRepository
               .findAll()
               .stream()
               .filter(category -> category.getLevel() == 1)
               .map(category -> modelMapper.map(category, CategoryDtoResponse.class))
               .collect(Collectors.toList());
    }

    public CategoryDtoResponse addCategory(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException(DUPLICATE_CATEGORY_NAME);
        }
        Category category = modelMapper.map(categoryDto, Category.class);

        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDtoResponse.class);
    }

    @Override
    public CategoryDtoResponse renameCategory(long id, String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ResourceAlreadyExistsException(DUPLICATE_CATEGORY_NAME);
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
        category.setName(name);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDtoResponse.class);
    }

    @Override
    public CategoryDtoResponse updateCategory(long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        category.setName(categoryDto.getName());
        category.setLevel(categoryDto.getLevel());
        categoryRepository.save(category);

        return modelMapper.map(category, CategoryDtoResponse.class);
    }

    public AttributeDtoResponse addAttribute(long categoryId, long attributeId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new ResourceDoesNotExistException("ddd"));
        category.getAttributes().add(attribute);
        categoryRepository.save(category);
        return modelMapper.map(attribute, AttributeDtoResponse.class);
    }

    @Override
    public void setCategoryImageName(long id, String name) {
        System.out.println(name);
        CategoryDtoResponse category = getCategoryById(id);
        Category mappedCategory = modelMapper.map(category, Category.class);
        mappedCategory.setImageName(name);
        categoryRepository.save(mappedCategory);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
        categoryRepository.delete(category);
    }
}
