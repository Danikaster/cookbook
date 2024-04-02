package com.cookbook.cookbook.dto.category;

import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;

import java.util.List;

public class CategoryDTO {
    private long id;
    private String name;
    private List<RecipeNameDTO> recipes;

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipes=" + recipes +
                '}';
    }

    public CategoryDTO(long id, String name, List<RecipeNameDTO> recipes) {
        this.id = id;
        this.name = name;
        this.recipes = recipes;
    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public List<RecipeNameDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeNameDTO> recipes) {
        this.recipes = recipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
