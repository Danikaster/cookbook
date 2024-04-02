package com.cookbook.cookbook.mapper.ingredient;

import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;
import com.cookbook.cookbook.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class IngredientNameDTOMapper implements Function<Ingredient, IngredientNameDTO> {
    @Override
    public IngredientNameDTO apply(Ingredient ingredient) {
        return new IngredientNameDTO(ingredient.getId(), ingredient.getName());
    }
}
