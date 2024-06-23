package com.example.cielobackend.util;

import com.example.cielobackend.model.*;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarSpecification extends AbstractSpecification<Listing> {

    public CarSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        super(parameters, childCategoryIds);
    }

    protected void addPredicates(List<Predicate> predicates, Root<Listing> root, CriteriaBuilder criteriaBuilder) {
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
}
