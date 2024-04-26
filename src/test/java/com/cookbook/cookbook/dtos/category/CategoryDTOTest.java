package com.cookbook.cookbook.dtos.category;

import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryDTOTest {

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO(1, "Test Category", new ArrayList<>());
    }

    @Test
    void testConstructorWithName() {
        String categoryName = "Test Category";
        CategoryDTO categoryDTO = new CategoryDTO(categoryName);
        assertEquals(categoryName, categoryDTO.getName());
    }

    @Test
    void testGetId() {
        assertEquals(1, categoryDTO.getId());
    }

    @Test
    void testSetId() {
        categoryDTO.setId(2);
        assertEquals(2, categoryDTO.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Test Category", categoryDTO.getName());
    }

    @Test
    void testSetName() {
        categoryDTO.setName("New Test Category");
        assertEquals("New Test Category", categoryDTO.getName());
    }

    @Test
    void testGetRecipes() {
        assertNotNull(categoryDTO.getRecipes());
    }

    @Test
    void testSetRecipes() {
        List<RecipeNameDTO> recipes = new ArrayList<>();
        recipes.add(new RecipeNameDTO("Recipe 1"));
        recipes.add(new RecipeNameDTO("Recipe 2"));
        categoryDTO.setRecipes(recipes);
        assertEquals(2, categoryDTO.getRecipes().size());
    }

    @Test
    void testToString() {
        assertEquals("CategoryDTO{id=1, name='Test Category', recipes=[]}", categoryDTO.toString());
    }
}


