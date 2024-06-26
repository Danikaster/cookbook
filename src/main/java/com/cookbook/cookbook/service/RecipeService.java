package com.cookbook.cookbook.service;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.exceptions.BadRequestException;
import com.cookbook.cookbook.exceptions.ResourceNotFoundException;
import com.cookbook.cookbook.exceptions.ServerException;
import com.cookbook.cookbook.mapper.recipe.RecipeDTOMapper;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.repository.CategoryRepository;
import com.cookbook.cookbook.repository.IngredientRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeDTOMapper recipeMapper;
    private static final String ERROR_MESSAGE = " does not exist";
    private final EntityCache<String, Recipe> recipeCache;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, CategoryRepository categoryRepository, RecipeDTOMapper recipeMapper, EntityCache<String, Recipe> recipeCache) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.recipeMapper = recipeMapper;
        this.recipeCache = recipeCache;
    }

    public List<RecipeDTO> getRecipes() {
        return recipeRepository.findAll().stream().map(recipeMapper).toList();
    }

    public void addNewRecipe(Recipe recipe) {

        if (Objects.equals(recipe.getName(), "")) {
            throw new BadRequestException("Name of recipe is empty");
        }
        if (recipeRepository.findByName(recipe.getName()) != null) {
            throw new ServerException("Recipe already exist");
        }
        if (recipe.getCategory()!=null && categoryRepository.getById(recipe.getCategory().getId()) == null) {
            throw new BadRequestException("There is no such category");
        }
        if(recipe.getCategory()!=null){
            recipe.setCategory(categoryRepository.getById(recipe.getCategory().getId()));
        }

        List<Ingredient> ingredients = recipe.getIngredients();
        List<Ingredient> allIngredients = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            Ingredient existingIngredient = ingredientRepository.getById(ingredient.getId());
            if (existingIngredient != null) {
                allIngredients.add(existingIngredient);
            } else {
                throw new ResourceNotFoundException("Ingredient with name " + ingredient.getName() + ERROR_MESSAGE);
            }
        }
        recipe.setIngredients(allIngredients);

        recipeRepository.save(recipe);

    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.getById(id);
        if (recipe != null) {
            recipeRepository.deleteById(id);
            recipeCache.remove(recipe.getName());
        } else {
            throw new ResourceNotFoundException("Recipe with id " + id + ERROR_MESSAGE);
        }
    }

    public RecipeDTO findByName(String name) {
        Recipe recipe = recipeCache.get(name);
        if (recipe == null) {
            recipe = recipeRepository.findByName(name);
        }
        if (recipe != null) {
            recipeCache.put(name, recipe);
            return recipeMapper.apply(recipe);
        } else {
            throw new ResourceNotFoundException("Recipe with name " + name + ERROR_MESSAGE);
        }
    }

    public void updateRecipe(Long id, String name, String category) {
        Optional<Recipe> oldRecipe = recipeRepository.findById(id);
        if (oldRecipe.isPresent()) {
            Recipe newRecipe = oldRecipe.get();
            recipeCache.remove(newRecipe.getName());
            newRecipe.setName(name);
            if(category != null)
                newRecipe.setCategory(categoryRepository.findByName(category));
            else
                newRecipe.setCategory(null);
            recipeRepository.save(newRecipe);
            recipeCache.put(name, newRecipe);
        } else {
            throw new ResourceNotFoundException("Recipe with id " + id + ERROR_MESSAGE);
        }
    }

}
