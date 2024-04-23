package com.cookbook.cookbook.models;

import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = new Recipe();
    }

    @Test
    public void testRecipeConstructorWithCategory() {
        // Create a category
        Category category = new Category("Test Category");

        // Create a recipe with the category
        Recipe recipe = new Recipe(category);

        // Verify the category
        assertEquals(category, recipe.getCategory());
    }

    @Test
    public void testRecipeConstructorWithNameAndCategory() {
        // Create a category
        Category category = new Category("Test Category");

        // Create a recipe with a name and the category
        Recipe recipe = new Recipe("Test Recipe", category);

        // Verify the name and category
        assertEquals("Test Recipe", recipe.getName());
        assertEquals(category, recipe.getCategory());
    }

    @Test
    public void testToString() {
        // Set values for the recipe
        recipe.setId(1L);
        recipe.setName("Test Recipe");

        // Verify the toString method
        assertEquals("RecipeModel{id=1, name='Test Recipe'}", recipe.toString());
    }

    @Test
    public void testGetId() {
        // Set id for the recipe
        recipe.setId(1L);

        // Verify the getId method
        assertEquals(1L, recipe.getId());
    }
}
