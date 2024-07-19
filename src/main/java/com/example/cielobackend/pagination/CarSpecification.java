package com.example.cielobackend.pagination;

import com.example.cielobackend.model.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class CarSpecification extends AbstractSpecification<Listing> implements SpecificationProvider {
    public CarSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        super(parameters, childCategoryIds);
    }

    @Override
    public String getCategoryName() {
        return "Autos, Caravans, Boats";
    }

    public void addPredicates(List<Predicate> predicates, Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        addPredicate(predicates, "Eurostandard", getListParameter("eurostandard"), criteriaBuilder);
        addPredicate(predicates, "Doors", getListParameter("doors"), criteriaBuilder);
        addPredicate(predicates, "Engine count", getListParameter("engineCount"), criteriaBuilder);
        addPredicate(predicates, "Seats", getListParameter("seats"), criteriaBuilder);
        addPredicate(predicates, "Condition", getListParameter("condition"), criteriaBuilder);
        addPredicate(predicates, "Engine", getListParameter("engine"), criteriaBuilder);
        addPredicate(predicates, "Coupe", getListParameter("coupe"), criteriaBuilder);
        addPredicate(predicates, "Model", getListParameter("model"), criteriaBuilder);
        addPredicate(predicates, "Colour", getListParameter("colour"), criteriaBuilder);
        addPredicate(predicates, "Gearbox", getListParameter("gearbox"), criteriaBuilder);
        addPredicate(predicates, "Terms of sale", getListParameter("termsOfSale"), criteriaBuilder);
        addPredicate(predicates, "Imported from", getListParameter("importedFrom"), criteriaBuilder);
        addPredicate(predicates, "Comfort", getListParameter("comfort"), criteriaBuilder);
        addPredicate(predicates, "Safety", getListParameter("safety"), criteriaBuilder);
        addPredicate(predicates, "Others", getListParameter("others"), criteriaBuilder);
        addPredicate(predicates, "Heating", getListParameter("heating"), criteriaBuilder);
        addPredicate(predicates, "Type", getListParameter("type"), criteriaBuilder);
    }

    @Override
    public AbstractSpecification<Listing> createSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        return new CarSpecification(parameters, childCategoryIds);
    }
}
