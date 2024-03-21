package com.cookbook.cookbook.mapper.recipe;

import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;
import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.model.RecipeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RecipeDTOMapper implements Function<RecipeModel, RecipeDTO> {
    @Override
    public RecipeDTO apply(RecipeModel recipe){
        List<IngredientNameDTO> ingredientsName = recipe.getIngredients().stream().map(ingredient -> new IngredientNameDTO(ingredient.getName())).toList();
        return new RecipeDTO(recipe.getId(), recipe.getName(), ingredientsName);
    }
}
