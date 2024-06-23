package com.example.cielobackend.util;

import com.example.cielobackend.model.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class SpecificationFactory {
    private final Map<String, Class<? extends AbstractSpecification<Listing>>> specificationMap;

    public SpecificationFactory() {
        this.specificationMap = new HashMap<>();
        initializeSpecifications(); // Initialize specifications map
    }

    private void initializeSpecifications() {
        // Add mappings of category ID to specification classes
        specificationMap.put("Real Estate", RealEstateSpecification.class); // Example: Apartment category ID
        specificationMap.put("Autos, Caravans, Boats", RealEstateSpecification.class); // Example: Apartment category ID
        specificationMap.put("Auto parts, accessories, tires and rims", RealEstateSpecification.class); // Example: Apartment category ID
        // Add more mappings as needed
    }

    public AbstractSpecification<Listing> getSpecification(String categoryName, Map<String, String[]> parameters,
                                                           List<Integer> childCategoryIds) {
        Class<? extends AbstractSpecification<Listing>> specificationClass = specificationMap.get(categoryName);
        if (specificationClass != null) {
            try {
                return specificationClass.getDeclaredConstructor(Map.class, List.class).newInstance(parameters, childCategoryIds);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to instantiate specification class for category ID: " + categoryName, e);
            }
        } else {
            return new DefaultSpecification(new HashMap<>(), new ArrayList<>());
        }
    }
}
