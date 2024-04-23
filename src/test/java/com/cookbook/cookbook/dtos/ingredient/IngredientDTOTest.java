package com.cookbook.cookbook.dtos.ingredient;

import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class IngredientDTOTest {

    private IngredientDTO ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new IngredientDTO(1L, "TestIngredient", new ArrayList<>());
    }

    @Test
    void testConstructorWithParameters() {
        assertEquals(1L, ingredient.getId());
        assertEquals("TestIngredient", ingredient.getName());
        assertNotNull(ingredient.getRecipes());
        assertTrue(ingredient.getRecipes().isEmpty());
    }

    @Test
    void testSetName() {
        ingredient.setName("NewName");
        assertEquals("NewName", ingredient.getName());
    }

    @Test
    void testSetId() {
        ingredient.setId(2L);
        assertEquals(2L, ingredient.getId());
    }

    @Test
    void testSetRecipes() {
        List<RecipeNameDTO> recipes = new ArrayList<>();
        recipes.add(new RecipeNameDTO("Recipe1"));
        recipes.add(new RecipeNameDTO("Recipe2"));
        ingredient.setRecipes(recipes);
        assertEquals(2, ingredient.getRecipes().size());
    }

    @Test
    void testToString() {
        String expectedString = "IngredientDTO{id=1, name='TestIngredient', recipes=[]}";
        assertEquals(expectedString, ingredient.toString());
    }
}

