package com.cookbook.cookbook.dtos.recipe;
import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecipeNameDTOTest {

    @Test
    public void testGetName() {
        RecipeNameDTO recipe = new RecipeNameDTO("Pasta");
        assertEquals("Pasta", recipe.getName());
    }

    @Test
    public void testSetName() {
        RecipeNameDTO recipe = new RecipeNameDTO();
        recipe.setName("Pizza");
        assertEquals("Pizza", recipe.getName());
    }

    @Test
    public void testGetId() {
        RecipeNameDTO recipe = new RecipeNameDTO(1, "Cake");
        assertEquals(1, recipe.getId());
    }

    @Test
    public void testSetId() {
        RecipeNameDTO recipe = new RecipeNameDTO();
        recipe.setId(5);
        assertEquals(5, recipe.getId());
    }

    @Test
    public void testToString() {
        RecipeNameDTO recipe = new RecipeNameDTO(3, "Salad");
        assertEquals("RecipeNameDTO{id=3, name='Salad'}", recipe.toString());
    }
}
