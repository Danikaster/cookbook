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
    void testRecipeConstructorWithCategory() {
        Category category = new Category("Test Category");

        Recipe recipe = new Recipe(category);

        assertEquals(category, recipe.getCategory());
    }

    @Test
    void testRecipeConstructorWithNameAndCategory() {
        Category category = new Category("Test Category");

        Recipe recipe = new Recipe("Test Recipe", category);

        assertEquals("Test Recipe", recipe.getName());
        assertEquals(category, recipe.getCategory());
    }

    @Test
    void testToString() {
        recipe.setId(1L);
        recipe.setName("Test Recipe");

        assertEquals("RecipeModel{id=1, name='Test Recipe'}", recipe.toString());
    }

    @Test
    void testGetId() {
        recipe.setId(1L);

        assertEquals(1L, recipe.getId());
    }
}
