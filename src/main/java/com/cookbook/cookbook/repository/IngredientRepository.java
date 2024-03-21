package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.IngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientModel, Long> {

    IngredientModel findByName(String name);

    void deleteByName(String name);
}
