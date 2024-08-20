package com.example.cielobackend.pagination;

import com.example.cielobackend.model.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class DefaultSpecification extends AbstractSpecification<Listing> implements SpecificationProvider {
    public DefaultSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        super(parameters, childCategoryIds);
    }

    @Override
    protected void addPredicates(List<Predicate> predicates, Root<Listing> root, CriteriaBuilder criteriaBuilder) {

    }

    @Override
    public String getCategoryName() {
        return "Default";
    }

    @Override
    public AbstractSpecification<Listing> createSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        return new DefaultSpecification(parameters, childCategoryIds);
    }
}
