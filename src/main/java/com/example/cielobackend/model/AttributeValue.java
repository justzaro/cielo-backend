package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Entity
@Table(name = "attribute_values")
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_value_id")
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @ManyToMany(mappedBy = "attributeValues")
    private Set<ListingAttribute> listingAttributes;
}
