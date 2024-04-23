package com.cookbook.cookbook.services;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.exceptions.BadRequestException;
import com.cookbook.cookbook.exceptions.ResourceNotFoundException;
import com.cookbook.cookbook.mapper.ingredient.IngredientDTOMapper;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.repository.IngredientRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import com.cookbook.cookbook.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientDTOMapper ingredientMapper;

    @Mock
    private EntityCache<String, Ingredient> ingredientCache;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getIngredients_ReturnsListOfIngredients() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));
        when(ingredientMapper.apply(any())).thenReturn(new IngredientDTO());

        // Act
        List<IngredientDTO> ingredients = ingredientService.getIngredients();

        // Assert
        assertFalse(ingredients.isEmpty());
    }

    @Test
    void addNewIngredient_ThrowsBadRequestException_WhenNameIsEmpty() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setName("");

        // Act & Assert
        assertThrows(BadRequestException.class, () -> ingredientService.addNewIngredient(ingredient));
    }

    @Test
    void addNewIngredient_ThrowsBadRequestException_WhenIngredientAlreadyExists() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        when(ingredientRepository.findByName(anyString())).thenReturn(ingredient);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> ingredientService.addNewIngredient(ingredient));
    }

    @Test
    void addNewIngredient_SavesNewIngredient_WhenValid() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        when(ingredientRepository.findByName(anyString())).thenReturn(null);

        // Act
        ingredientService.addNewIngredient(ingredient);

        // Assert
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void deleteIngredient_RemovesIngredientFromRepositoryAndCache() {
        // Arrange
        String ingredientName = "TestIngredient";
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        when(ingredientRepository.findByName(ingredientName)).thenReturn(ingredient);

        // Act
        ingredientService.deleteIngredient(ingredientName);

        // Assert
        verify(ingredientRepository, times(1)).deleteByName(ingredientName);
        verify(ingredientCache, times(1)).remove(ingredientName);
    }

    @Test
    void deleteIngredient_ThrowsResourceNotFoundException_WhenIngredientNotFound() {
        // Arrange
        String ingredientName = "NonExistentIngredient";
        when(ingredientRepository.findByName(ingredientName)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> ingredientService.deleteIngredient(ingredientName));
    }

    @Test
    void findByName_ReturnsIngredientDTO_WhenIngredientFoundInCache() {
        // Arrange
        String ingredientName = "TestIngredient";
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        when(ingredientCache.get(ingredientName)).thenReturn(ingredient);
        when(ingredientMapper.apply(ingredient)).thenReturn(new IngredientDTO());

        // Act
        IngredientDTO foundIngredient = ingredientService.findByName(ingredientName);

        // Assert
        assertNotNull(foundIngredient);
    }
    @Test
    void findByName_ReturnsIngredientDTO_WhenIngredientFound() {
        // Arrange
        String ingredientName = "TestIngredient";
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        when(ingredientRepository.findByName(ingredientName)).thenReturn(ingredient);
        when(ingredientMapper.apply(ingredient)).thenReturn(new IngredientDTO());

        // Act
        IngredientDTO foundIngredient = ingredientService.findByName(ingredientName);

        // Assert
        assertNotNull(foundIngredient);
    }

    @Test
    void findByName_ThrowsResourceNotFoundException_WhenIngredientNotFound() {
        // Arrange
        String ingredientName = "NonExistentIngredient";
        when(ingredientCache.get(ingredientName)).thenReturn(null);
        when(ingredientRepository.findByName(ingredientName)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> ingredientService.findByName(ingredientName));
    }


    @Test
    void updateIngredient_UpdatesIngredientName_WhenIngredientExists() {
        // Arrange
        Long ingredientId = 1L;
        String newName = "NewName";
        Ingredient oldIngredient = new Ingredient();
        oldIngredient.setId(ingredientId);
        oldIngredient.setName("OldName");
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(oldIngredient));

        // Act
        ingredientService.updateIngredient(ingredientId, newName);

        // Assert
        assertEquals(newName, oldIngredient.getName());
    }

    @Test
    void updateIngredient_ThrowsResourceNotFoundException_WhenIngredientNotFound() {
        // Arrange
        Long nonExistentId = 999L;
        String newName = "NewName";
        when(ingredientRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> ingredientService.updateIngredient(nonExistentId, newName));
    }
}
