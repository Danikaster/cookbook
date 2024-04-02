package com.cookbook.cookbook.mapper.recipe;

import com.cookbook.cookbook.dto.category.CategoryNameDTO;
import com.cookbook.cookbook.dto.ingredient.IngredientNameDTO;
import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RecipeDTOMapper implements Function<Recipe, RecipeDTO> {
    @Override
    public RecipeDTO apply(Recipe recipe) {

        List<IngredientNameDTO> ingredientsName = recipe.getIngredients().stream().map(ingredient -> new IngredientNameDTO(ingredient.getId(), ingredient.getName())).toList();
        Category category = recipe.getCategory();
        CategoryNameDTO categoryName = category != null ? new CategoryNameDTO(category.getName()) : null;

        return new RecipeDTO(recipe.getId(), recipe.getName(), categoryName, ingredientsName);
    }
}
