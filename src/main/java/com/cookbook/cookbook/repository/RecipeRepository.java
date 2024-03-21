package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.RecipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeModel, Long> {
    RecipeModel findByName(String name);

    void deleteByName(String name);
}
