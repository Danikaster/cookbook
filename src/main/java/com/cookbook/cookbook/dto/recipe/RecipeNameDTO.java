package com.cookbook.cookbook.dto.recipe;

public class RecipeNameDTO {
    private String name;

    public RecipeNameDTO() {
    }

    public RecipeNameDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RecipeNameDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
