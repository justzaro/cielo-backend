package com.example.cielobackend.pagination;

import com.example.cielobackend.model.*;
import jakarta.persistence.criteria.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public abstract class AbstractSpecification<T> implements Specification<T> {
    private Map<String, String[]> parameters;
    private List<Integer> childCategoryIds;
    private Join<Listing, ListingDetail> joinDetails;
    private Join<ListingDetail, ListingDetailValue> joinDetailValues;
    private Join<ListingDetailValue, AttributeValue> joinAttributeValues;
    private Join<ListingDetail, Attribute> joinAttributes;

    public AbstractSpecification(Map<String, String[]> parameters, List<Integer> childCategoryIds) {
        this.parameters = parameters;
        this.childCategoryIds = childCategoryIds;
    }

    protected void setupJoins(Root<T> root) {
        joinDetails = root.join("details", JoinType.INNER);
        joinDetailValues = joinDetails.join("detailValues", JoinType.INNER);
        joinAttributeValues = joinDetailValues.join("attributeValue", JoinType.INNER);
        joinAttributes = joinDetails.join("attribute", JoinType.INNER);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        query.distinct(true);
        setupJoins(root);
        addCommonPredicates(predicates, root, criteriaBuilder);
        addPredicates(predicates, root, criteriaBuilder);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    protected void addCommonPredicates(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder) {
        int minPrice = getIntParameter("minPrice", 0);
        int maxPrice = getIntParameter("maxPrice", 100000000);

        predicates.add(criteriaBuilder.between(root.get("price"), minPrice, maxPrice));

        if (childCategoryIds != null && !childCategoryIds.isEmpty()) {
            predicates.add(root.get("category").get("id").in(childCategoryIds));
        }

        if (parameters.containsKey("userId")) {
            int userId = getIntParameter("userId", 0);
            Predicate predicate = criteriaBuilder.equal(root.get("user").get("id"), userId);
            predicates.add(predicate);
        }

        String searchText = getStringParameter("searchText");
        if (searchText != null && !searchText.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + searchText + "%"));
        }
    }

    protected abstract void addPredicates(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder);

    protected void addPredicate(List<Predicate> predicates,
                                String attributeName, List<String> values,
                                CriteriaBuilder criteriaBuilder) {
        if (values != null && !values.isEmpty()) {
            Predicate predicate = criteriaBuilder.and(
                    criteriaBuilder.equal(joinAttributes.get("name"), attributeName),
                    joinAttributeValues.get("value").in(values)
            );
            predicates.add(predicate);
        }
    }

    protected List<String> getListParameter(String paramName) {
        String[] values = parameters.get(paramName);
        if (values != null) {
            return List.of(values);
        } else {
            return List.of();
        }
    }

    protected String getStringParameter(String paramName) {
        String[] values = parameters.get(paramName);
        if (values != null && values.length > 0) {
            return values[0];
        } else {
            return null;
        }
    }

    protected Integer getIntParameter(String paramName, int defaultValue) {
        String[] values = parameters.get(paramName);
        if (values != null && values.length > 0) {
            try {
                return Integer.parseInt(values[0]);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}