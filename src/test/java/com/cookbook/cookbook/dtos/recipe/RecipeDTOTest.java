package com.cookbook.cookbook.dtos.recipe;

import com.cookbook.cookbook.dto.category.CategoryNameDTO;
import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;
import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeDTOTest {

    @Test
    void testEmptyConstructor() {
        RecipeDTO recipeDTO = new RecipeDTO();
        assertNotNull(recipeDTO);
    }

    @Test
    void testConstructorWithName() {
        RecipeDTO recipeDTO = new RecipeDTO("Test Recipe");
        assertEquals("Test Recipe", recipeDTO.getName());
    }

    @Test
    void testFullConstructor() {
        CategoryNameDTO category = Mockito.mock(CategoryNameDTO.class);
        List<IngredientNameDTO> ingredients = Arrays.asList(
                Mockito.mock(IngredientNameDTO.class),
                Mockito.mock(IngredientNameDTO.class)
        );

        RecipeDTO recipeDTO = new RecipeDTO(1, "Test Recipe", category, ingredients);

        assertEquals(1, recipeDTO.getId());
        assertEquals("Test Recipe", recipeDTO.getName());
        assertEquals(category, recipeDTO.getCategory());
        assertEquals(ingredients, recipeDTO.getIngredients());
    }

    @Test
    void testGettersAndSetters() {
        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setId(1);
        recipeDTO.setName("Test Recipe");
        CategoryNameDTO category = Mockito.mock(CategoryNameDTO.class);
        recipeDTO.setCategory(category);
        List<IngredientNameDTO> ingredients = Arrays.asList(
                Mockito.mock(IngredientNameDTO.class),
                Mockito.mock(IngredientNameDTO.class)
        );
        recipeDTO.setIngredients(ingredients);

        assertEquals(1, recipeDTO.getId());
        assertEquals("Test Recipe", recipeDTO.getName());
        assertEquals(category, recipeDTO.getCategory());
        assertEquals(ingredients, recipeDTO.getIngredients());
    }

    @Test
    void testToString() {
        CategoryNameDTO category = Mockito.mock(CategoryNameDTO.class);
        List<IngredientNameDTO> ingredients = Arrays.asList(
                Mockito.mock(IngredientNameDTO.class),
                Mockito.mock(IngredientNameDTO.class)
        );

        RecipeDTO recipeDTO = new RecipeDTO(1, "Test Recipe", category, ingredients);
        String expectedString = "RecipeDTO{id=1, name='Test Recipe', category=" + category.toString() +
                ", ingredients=" + ingredients.toString() + '}';
        assertEquals(expectedString, recipeDTO.toString());
    }
}


