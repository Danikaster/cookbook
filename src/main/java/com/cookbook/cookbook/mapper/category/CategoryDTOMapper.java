package com.cookbook.cookbook.mapper.category;

import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.dto.recipe.RecipeNameDTO;
import com.cookbook.cookbook.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class CategoryDTOMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        List<RecipeNameDTO> recipesName = category.getRecipes().stream().map(recipe -> new RecipeNameDTO(recipe.getId(), recipe.getName())).toList();
        return new CategoryDTO(category.getId(), category.getName(), recipesName);
    }
}
