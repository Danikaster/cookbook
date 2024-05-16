package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE r.name = :name")
    Recipe findByName(@Param("name") String name);
    Recipe getById(Long id);

    void deleteByName(String name);
}
