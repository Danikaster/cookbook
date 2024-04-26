package com.cookbook.cookbook.models;

import com.cookbook.cookbook.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
    }

    @Test
    void testIngredientConstructorWithName() {
        Ingredient ingredient = new Ingredient("Test Ingredient");

        assertEquals("Test Ingredient", ingredient.getName());
    }

    @Test
    void testToString() {
        ingredient.setId(1L);
        ingredient.setName("Test Ingredient");

        assertEquals("IngredientModel{id=1, name='Test Ingredient'}", ingredient.toString());
    }
}

