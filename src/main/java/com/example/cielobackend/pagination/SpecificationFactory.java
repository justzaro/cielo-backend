package com.example.cielobackend.pagination;

import com.example.cielobackend.model.Listing;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SpecificationFactory {
    @Value("${reflection.pagination.path}")
    private String reflectionPath;
    private final Map<String, SpecificationProvider> providers = new HashMap<>();

    @PostConstruct
    private void initializeSpecifications() {
        Reflections reflections = new Reflections(reflectionPath);
        Set<Class<? extends SpecificationProvider>> providerClasses = reflections.getSubTypesOf(SpecificationProvider.class);

        for (Class<? extends SpecificationProvider> providerClass : providerClasses) {
            try {
                SpecificationProvider provider = providerClass.getDeclaredConstructor().newInstance();
                providers.put(provider.getCategoryName(), provider);
            } catch (Exception e) {
                log.error("Failed to instantiate provider {}: {}", providerClass.getName(), e);
            }
        }
    }

    public AbstractSpecification<Listing> getSpecification(String categoryName, Map<String, String[]> parameters,
                                                           List<Integer> childCategoryIds) {
        SpecificationProvider provider = providers.get(categoryName);

        if (provider != null) {
            return provider.createSpecification(parameters, childCategoryIds);
        }

        return new DefaultSpecification(parameters, childCategoryIds);
    }
}
