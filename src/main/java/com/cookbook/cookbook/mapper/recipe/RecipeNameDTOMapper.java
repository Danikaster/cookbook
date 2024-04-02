package com.cookbook.cookbook.mapper.recipe;

import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import com.cookbook.cookbook.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecipeNameDTOMapper implements Function<Recipe, RecipeNameDTO> {
    @Override
    public RecipeNameDTO apply(Recipe recipe) {
        return new RecipeNameDTO(recipe.getId(), recipe.getName());
    }
}
