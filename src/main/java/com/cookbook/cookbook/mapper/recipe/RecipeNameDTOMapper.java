package com.cookbook.cookbook.mapper.recipe;

import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import com.cookbook.cookbook.model.RecipeModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecipeNameDTOMapper implements Function<RecipeModel, RecipeNameDTO> {
    @Override
    public RecipeNameDTO apply(RecipeModel recipe) {
        return new RecipeNameDTO(recipe.getName());
    }
}
