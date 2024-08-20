package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

@Data
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "province")
    private List<City> cities;
}
