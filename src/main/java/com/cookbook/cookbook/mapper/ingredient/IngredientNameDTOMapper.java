package com.cookbook.cookbook.mapper.ingredient;

import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;
import com.cookbook.cookbook.model.IngredientModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class IngredientNameDTOMapper implements Function<IngredientModel, IngredientNameDTO>{
    @Override
    public IngredientNameDTO apply(IngredientModel ingredient) {
        return new IngredientNameDTO(ingredient.getName());
    }
}
