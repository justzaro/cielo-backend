package com.example.cielobackend.util;

import com.example.cielobackend.model.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Map;

public class DefaultSpecification extends AbstractSpecification<Listing> {

    public DefaultSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        super(parameters, childCategoryIds);
    }

    @Override
    protected void addPredicates(List<Predicate> predicates, Root<Listing> root, CriteriaBuilder criteriaBuilder) {

    }
}
