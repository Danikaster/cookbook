package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
    Ingredient getById(Long id);

    void deleteByName(String name);
}
