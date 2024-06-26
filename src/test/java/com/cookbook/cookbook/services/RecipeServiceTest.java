package com.cookbook.cookbook.services;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.exceptions.BadRequestException;
import com.cookbook.cookbook.exceptions.ResourceNotFoundException;
import com.cookbook.cookbook.exceptions.ServerException;
import com.cookbook.cookbook.mapper.recipe.RecipeDTOMapper;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.repository.CategoryRepository;
import com.cookbook.cookbook.repository.IngredientRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import com.cookbook.cookbook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RecipeDTOMapper recipeMapper;

    @Mock
    private EntityCache<String, Recipe> recipeCache;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRecipes_ReturnsListOfRecipes() {
        Recipe recipe = new Recipe();
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        when(recipeMapper.apply(any())).thenReturn(new RecipeDTO());

        List<RecipeDTO> recipes = recipeService.getRecipes();

        assertFalse(recipes.isEmpty());
    }

    @Test
    void addNewRecipe_ThrowsBadRequestException_WhenNameIsEmpty() {
        Recipe recipe = new Recipe();
        recipe.setName("");

        assertThrows(BadRequestException.class, () -> recipeService.addNewRecipe(recipe));
    }

    @Test
    void addNewRecipe_ThrowsServerException_WhenRecipeAlreadyExists() {
        Recipe recipe = new Recipe();
        recipe.setName("TestRecipe");
        when(recipeRepository.findByName(anyString())).thenReturn(recipe);

        assertThrows(ServerException.class, () -> recipeService.addNewRecipe(recipe));
    }

    @Test
    void addNewRecipe_ThrowsBadRequestException_WhenCategoryNotFoundAndNameNotEmpty() {
        Recipe recipe = new Recipe();
        recipe.setName("TestRecipe");
        Category category = new Category();
        category.setName("NonExistentCategory");
        recipe.setCategory(category);
        when(categoryRepository.findByName("NonExistentCategory")).thenReturn(null);

        assertThrows(BadRequestException.class, () -> recipeService.addNewRecipe(recipe));
    }

    /*@Test
    void addNewRecipe_ThrowsResourceNotFoundException_WhenIngredientNotFound() {
        Recipe recipe = new Recipe();
        recipe.setName("TestRecipe");
        Category category = new Category();
        category.setName("TestCategory");
        recipe.setCategory(category);
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setName("NonExistentIngredient");
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);
        when(categoryRepository.findByName("TestCategory")).thenReturn(category);
        when(ingredientRepository.findByName("NonExistentIngredient")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> recipeService.addNewRecipe(recipe));
    }*/


    /*@Test
    void addNewRecipe_SavesRecipeToRepository() {
        Recipe recipe = new Recipe();
        recipe.setName("TestRecipe");
        Category category = new Category();
        category.setName("TestCategory");
        recipe.setCategory(category);
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);
        when(categoryRepository.findByName("TestCategory")).thenReturn(category);
        when(ingredientRepository.findByName("TestIngredient")).thenReturn(ingredient);

        recipeService.addNewRecipe(recipe);

        verify(recipeRepository, times(1)).save(recipe);
    }*/

    /*@Test
    void deleteRecipe_RemovesRecipeFromRepositoryAndCache() {
        String recipeName = "TestRecipe";
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        when(recipeRepository.findByName(recipeName)).thenReturn(recipe);

        recipeService.deleteRecipe(recipeName);

        verify(recipeRepository, times(1)).deleteByName(recipeName);
        verify(recipeCache, times(1)).remove(recipeName);
    }*/

    /*@Test
    void deleteRecipe_ThrowsResourceNotFoundException_WhenRecipeNotFound() {
        String recipeName = "NonExistentRecipe";
        when(recipeRepository.findByName(recipeName)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> recipeService.deleteRecipe(recipeName));
    }*/

    @Test
    void findByName_ReturnsRecipeDTO_WhenRecipeFoundInCache() {
        String recipeName = "TestRecipe";
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        when(recipeCache.get(recipeName)).thenReturn(recipe);
        when(recipeMapper.apply(recipe)).thenReturn(new RecipeDTO());

        RecipeDTO foundRecipe = recipeService.findByName(recipeName);

        assertNotNull(foundRecipe);
    }

    @Test
    void findByName_ThrowsResourceNotFoundException_WhenRecipeNotFound() {
        String recipeName = "NonExistentRecipe";
        when(recipeCache.get(recipeName)).thenReturn(null);
        when(recipeRepository.findByName(recipeName)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> recipeService.findByName(recipeName));
    }

    /*@Test
    void updateRecipe_UpdatesRecipeName_WhenRecipeExists() {
        Long recipeId = 1L;
        String newName = "NewName";
        Recipe oldRecipe = new Recipe();
        oldRecipe.setId(recipeId);
        oldRecipe.setName("OldName");
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(oldRecipe));

        recipeService.updateRecipe(recipeId, newName);

        assertEquals(newName, oldRecipe.getName());
    }

    @Test
    void updateRecipe_ThrowsResourceNotFoundException_WhenRecipeNotFound() {
        Long nonExistentId = 999L;
        String newName = "NewName";
        when(recipeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recipeService.updateRecipe(nonExistentId, newName));
    }*/
}
