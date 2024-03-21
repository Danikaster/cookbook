package com.cookbook.cookbook.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class IngredientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    public IngredientModel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IngredientModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public IngredientModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeModel> recipes) {
        this.recipes = recipes;
    }

    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    private List<RecipeModel> recipes = new ArrayList<>();
}
