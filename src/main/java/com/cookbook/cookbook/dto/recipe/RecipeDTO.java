package com.cookbook.cookbook.dto.recipe;

import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;

import java.util.List;

public class RecipeDTO {
    private long id;
    private String name;
    private List<IngredientNameDTO> ingredients;

    public RecipeDTO(String name) {
        this.name = name;
    }

    public RecipeDTO() {
    }

    public RecipeDTO(long id, String name, List<IngredientNameDTO> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
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

    public List<IngredientNameDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientNameDTO> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
