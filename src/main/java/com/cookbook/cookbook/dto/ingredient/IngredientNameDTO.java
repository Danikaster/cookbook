package com.cookbook.cookbook.dto.ingredient;

public class IngredientNameDTO
{
    private String name;

    public IngredientNameDTO() {
    }

    public IngredientNameDTO(String name) {
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
        return "IngredientNameDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
