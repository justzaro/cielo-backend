package com.example.cielobackend.service.implementation;

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
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Value("${rabbitmq.images-exchange.name}")
    private String imagesExchange;
    @Value("${rabbitmq.deleted-images-q.routing-key}")
    private String deletedImagesQueueRoutingKey;
    private final RabbitTemplate rabbitTemplate;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

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

    public AttributeDtoResponse addAttributeToCategory(long categoryId, long attributeId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        category.getAttributes().add(attribute);
        categoryRepository.save(category);
        return modelMapper.map(attribute, AttributeDtoResponse.class);
    }

    @Override
    public void removeAttributeFromCategory(long categoryId, long attributeId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        category.getAttributes().remove(attribute);
        categoryRepository.save(category);
    }

    @Override
    public void setCategoryImageName(long id, String name) {
        CategoryDtoResponse category = getCategoryById(id);
        Category mappedCategory = modelMapper.map(category, Category.class);
        mappedCategory.setImageName(name);
        categoryRepository.save(mappedCategory);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
        sendCategoryImageToDeletionQueue(category.getImageName());
        categoryRepository.delete(category);
    }

    @Override
    public Long countAllListingsByCategory(long id) {
        return categoryRepository.countAllById(id);
    }

    private void sendCategoryImageToDeletionQueue(String imageName) {
        rabbitTemplate.convertAndSend(imagesExchange,
                                      deletedImagesQueueRoutingKey,
                                      imageName);
    }
}
