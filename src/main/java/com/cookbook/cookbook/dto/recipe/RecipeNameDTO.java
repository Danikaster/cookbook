package com.cookbook.cookbook.dto.recipe;

public class RecipeNameDTO {
    private long id;
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

    public long getId() {
        return id;
    }

    public RecipeNameDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RecipeNameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
