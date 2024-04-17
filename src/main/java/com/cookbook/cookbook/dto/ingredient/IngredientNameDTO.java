package com.cookbook.cookbook.dto.ingredient;

public class IngredientNameDTO {
    private long id;
    private String name;

    public IngredientNameDTO() {
    }

    public IngredientNameDTO(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public IngredientNameDTO(long id, String name) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
