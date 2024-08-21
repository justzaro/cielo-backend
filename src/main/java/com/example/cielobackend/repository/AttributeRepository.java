package com.example.cielobackend.repository;

import com.example.cielobackend.model.Attribute;
import com.example.cielobackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findAllByCategories(Set<Category> categories);
}
