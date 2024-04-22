package com.cookbook.cookbook.services;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.mapper.ingredient.IngredientDTOMapper;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.repository.IngredientRepository;
import com.cookbook.cookbook.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientDTOMapper ingredientMapper;

    @Mock
    private EntityCache<Integer, Ingredient> ingredientCache;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getIngredients() {
        Ingredient ingredient1 = new Ingredient("potato");
        Ingredient ingredient2 = new Ingredient("cucumber");
        IngredientDTO ingredient3 = new IngredientDTO("potato");
        IngredientDTO ingredient4 = new IngredientDTO("cucumber");

        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient1, ingredient2));
        when(ingredientMapper.apply(ingredient1)).thenReturn(ingredient3);
        when(ingredientMapper.apply(ingredient2)).thenReturn(ingredient4);

        List<IngredientDTO> ingredients = ingredientService.getIngredients();

        Assertions.assertEquals(List.of(ingredient3, ingredient4), ingredients);
    }
}
