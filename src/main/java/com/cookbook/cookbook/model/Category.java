package com.cookbook.cookbook.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    public Category(long id, String name, List<Recipe> recipes) {
        this.id = id;
        this.name = name;
        this.recipes = recipes;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
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

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipes=" + recipes +
                '}';
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    private List<Recipe> recipes = new ArrayList<>();


}
