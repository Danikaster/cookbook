package com.cookbook.cookbook.dto.ingredient;

import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;

import java.util.List;

public class IngredientDTO {
    private long id;
    private String name;
    private List<RecipeNameDTO> recipes;

    public IngredientDTO() {
    }

    public IngredientDTO(String name) {
        this.name = name;
    }

    public IngredientDTO(long id, String name, List<RecipeNameDTO> recipes) {
        this.id = id;
        this.name = name;
        this.recipes = recipes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeNameDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeNameDTO> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "IngredientDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipes=" + recipes +
                '}';
    }
}
