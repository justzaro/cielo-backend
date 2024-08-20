package com.example.cielobackend.pagination;

import com.example.cielobackend.model.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class RealEstateSpecification extends AbstractSpecification<Listing> implements SpecificationProvider {
    public RealEstateSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        super(parameters, childCategoryIds);
    }

    @Override
    protected void addPredicates(List<Predicate> predicates, Root<Listing> root, CriteriaBuilder criteriaBuilder) {

    }

    @Override
    public String getCategoryName() {
        return "Real Estate";
    }

    @Override
    public AbstractSpecification<Listing> createSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        return new RealEstateSpecification(parameters, childCategoryIds);
    }
}
