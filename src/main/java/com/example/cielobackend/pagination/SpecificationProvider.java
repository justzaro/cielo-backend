package com.example.cielobackend.pagination;

import com.example.cielobackend.model.Listing;

import java.util.List;
import java.util.Map;

public interface SpecificationProvider {
    String getCategoryName();
    AbstractSpecification<Listing> createSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds);
}
