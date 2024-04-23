package com.cookbook.cookbook.models;

import com.cookbook.cookbook.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {

    private Ingredient ingredient;

    @BeforeEach
    public void setUp() {
        ingredient = new Ingredient();
    }

    @Test
    public void testIngredientConstructorWithName() {
        // Create an ingredient
        Ingredient ingredient = new Ingredient("Test Ingredient");

        // Verify the value
        assertEquals("Test Ingredient", ingredient.getName());
    }

    @Test
    public void testToString() {
        // Set values for the ingredient
        ingredient.setId(1L);
        ingredient.setName("Test Ingredient");

        // Verify the toString method
        assertEquals("IngredientModel{id=1, name='Test Ingredient'}", ingredient.toString());
    }
}

