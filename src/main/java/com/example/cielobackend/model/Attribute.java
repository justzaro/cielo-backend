package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "attributes")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;
    @Column
    private String name;

    @OneToMany(mappedBy = "attribute")
    private List<ListingDetail> listingDetails;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<AttributeValue> attributeValues;

    @ManyToMany(mappedBy = "attributes")
    private Set<Category> categories;
}
