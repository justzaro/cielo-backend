package com.example.cielobackend.util;

import com.example.cielobackend.model.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RealEstateSpecification extends AbstractSpecification<Listing> {
    public RealEstateSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        super(parameters, childCategoryIds);
    }

    @Override
    protected void addPredicates(List list, Root root, CriteriaBuilder criteriaBuilder) {

    }
}
