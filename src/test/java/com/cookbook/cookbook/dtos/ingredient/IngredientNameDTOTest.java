package com.cookbook.cookbook.dtos.ingredient;

import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientNameDTOTest {

    @Test
    public void testConstructorWithName() {
        String name = "Test Ingredient";
        IngredientNameDTO ingredient = new IngredientNameDTO(name);
        assertEquals(name, ingredient.getName());
    }

    @Test
    public void testConstructorWithIdAndName() {
        long id = 1;
        String name = "Test Ingredient";
        IngredientNameDTO ingredient = new IngredientNameDTO(id, name);
        assertEquals(id, ingredient.getId());
        assertEquals(name, ingredient.getName());
    }

    @Test
    public void testSetName() {
        IngredientNameDTO ingredient = new IngredientNameDTO();
        String name = "Test Ingredient";
        ingredient.setName(name);
        assertEquals(name, ingredient.getName());
    }

    @Test
    public void testSetId() {
        IngredientNameDTO ingredient = new IngredientNameDTO();
        long id = 1;
        ingredient.setId(id);
        assertEquals(id, ingredient.getId());
    }

    @Test
    public void testToString() {
        long id = 1;
        String name = "Test Ingredient";
        IngredientNameDTO ingredient = new IngredientNameDTO(id, name);
        String expectedToString = "IngredientNameDTO{id=" + id + ", name='" + name + "'}";
        assertEquals(expectedToString, ingredient.toString());
    }
}
