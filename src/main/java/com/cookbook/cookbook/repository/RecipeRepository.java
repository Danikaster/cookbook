package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.RecipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeModel, Long> {

    @Query("SELECT r FROM RecipeModel r WHERE r.name = :name")
    RecipeModel findByName(@Param("name") String name);

    void deleteByName(String name);
}
