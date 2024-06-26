package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Category getById(Long id);

    void deleteByName(String name);
}
