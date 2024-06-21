package com.example.cielobackend.util;

import com.example.cielobackend.dto.ListingSearchParameters;
import com.example.cielobackend.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ListingSearchParametersSpecification implements Specification<Listing> {

    private final ListingSearchParameters params;

    public ListingSearchParametersSpecification(ListingSearchParameters params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        query.distinct(true);

        Join<Listing, ListingDetail> joinDetails = root.join("details", JoinType.INNER);
        Join<ListingDetail, ListingDetailValue> joinDetailValues = joinDetails.join("detailValues", JoinType.INNER);
        Join<ListingDetailValue, AttributeValue> joinAttributeValues = joinDetailValues.join("attributeValue", JoinType.INNER);
        Join<ListingDetail, Attribute> joinAttributes = joinDetails.join("attribute", JoinType.INNER);

        if (params.getEurostandard() != null && !params.getEurostandard().isEmpty()) {
            Predicate eurostandardPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Eurostandard"),
                    joinAttributeValues.get("value").in(params.getEurostandard())
            );
            predicates.add(eurostandardPredicate);
        }

        if (params.getDoors() != null && !params.getDoors().isEmpty()) {
            Predicate enginePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Doors"),
                    joinAttributeValues.get("value").in(params.getDoors())
            );
            predicates.add(enginePredicate);
        }

        if (params.getEngineCount() != null && !params.getEngineCount().isEmpty()) {
            Predicate enginePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Engine count"),
                    joinAttributeValues.get("value").in(params.getEngineCount())
            );
            predicates.add(enginePredicate);
        }

        if (params.getSeats() != null && !params.getSeats().isEmpty()) {
            Predicate enginePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Seats"),
                    joinAttributeValues.get("value").in(params.getSeats())
            );
            predicates.add(enginePredicate);
        }

        if (params.getCondition() != null && !params.getCondition().isEmpty()) {
            Predicate enginePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Condition"),
                    joinAttributeValues.get("value").in(params.getCondition())
            );
            predicates.add(enginePredicate);
        }

        if (params.getEngine() != null && !params.getEngine().isEmpty()) {
            Predicate enginePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Engine"),
                    joinAttributeValues.get("value").in(params.getEngine())
            );
            predicates.add(enginePredicate);
        }

        if (params.getCoupe() != null && !params.getCoupe().isEmpty()) {
            Predicate enginePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Coupe"),
                    joinAttributeValues.get("value").in(params.getCoupe())
            );
            predicates.add(enginePredicate);
        }

        if (params.getModel() != null && !params.getModel().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Model"),
                    joinAttributeValues.get("value").in(params.getModel())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getColour() != null && !params.getColour().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Colour"),
                    joinAttributeValues.get("value").in(params.getColour())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getGearbox() != null && !params.getGearbox().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Gearbox"),
                    joinAttributeValues.get("value").in(params.getGearbox())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getTermsOfSale() != null && !params.getTermsOfSale().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Terms of sale"),
                    joinAttributeValues.get("value").in(params.getTermsOfSale())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getImportedFrom() != null && !params.getImportedFrom().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Imported from"),
                    joinAttributeValues.get("value").in(params.getImportedFrom())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getComfort() != null && !params.getComfort().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Comfort"),
                    joinAttributeValues.get("value").in(params.getComfort())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getSafety() != null && !params.getSafety().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Safety"),
                    joinAttributeValues.get("value").in(params.getSafety())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getOthers() != null && !params.getOthers().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Others"),
                    joinAttributeValues.get("value").in(params.getOthers())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getHeating() != null && !params.getHeating().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Heating"),
                    joinAttributeValues.get("value").in(params.getHeating())
            );
            predicates.add(extrasPredicate);
        }

        if (params.getType() != null && !params.getType().isEmpty()) {
            Predicate extrasPredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), "Type"),
                    joinAttributeValues.get("value").in(params.getType())
            );
            predicates.add(extrasPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
