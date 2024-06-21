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
    @Query(value = """
    WITH RECURSIVE CategoryHierarchy AS (
        SELECT 
            c.category_id
        FROM 
            categories c
        WHERE 
            c.category_id = :categoryId
        UNION ALL
        SELECT 
            c.category_id
        FROM 
            categories c
        INNER JOIN 
            CategoryHierarchy ch ON c.parent_category_id = ch.category_id
    )
    SELECT 
        COUNT(l.listing_id) AS listing_count
    FROM 
        CategoryHierarchy ch
    LEFT JOIN 
        listings l ON ch.category_id = l.category_id;
    """,
    nativeQuery = true)
    Long countAllByCategoryId(@Param("categoryId") long id);
}
