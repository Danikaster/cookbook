package com.cookbook.cookbook.models;

import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;


class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void testCategoryConstructorWithIdAndNameAndRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        Category category = new Category(1L, "Test Category", recipes);

        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
        assertEquals(recipes, category.getRecipes());
    }

    @Test
    void testCategoryConstructorWithName() {
        Category category = new Category("Test Category");

        assertEquals("Test Category", category.getName());
    }

    @Test
    void testToString() {
        category.setId(1L);
        category.setName("Test Category");

        assertEquals("Category{id=1, name='Test Category', recipes=[]}", category.toString());
    }

    @Test
    void testGetId() {
        category.setId(1L);

        assertEquals(1L, category.getId());
    }
}
