package com.cookbook.cookbook.mapper.ingredient;

import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import com.cookbook.cookbook.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class IngredientDTOMapper implements Function<Ingredient, IngredientDTO> {
    @Override
    public IngredientDTO apply(Ingredient ingredient) {
        List<RecipeNameDTO> recipesName = ingredient.getRecipes().stream().map(recipe -> new RecipeNameDTO(recipe.getId(), recipe.getName())).toList();
        return new IngredientDTO(ingredient.getId(), ingredient.getName(), recipesName);
    }
}
