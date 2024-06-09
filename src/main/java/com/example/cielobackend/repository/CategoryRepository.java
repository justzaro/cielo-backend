package com.example.cielobackend.repository;

import com.example.cielobackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    @Query(value = "SELECT COUNT(l.listing_id) FROM listings AS l WHERE l.category_id = :id",
           nativeQuery = true)
    Long countAllByCategoryId(@Param("id") long id);
}
