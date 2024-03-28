package com.cookbook.cookbook.service;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.mapper.recipe.RecipeDTOMapper;
import com.cookbook.cookbook.model.IngredientModel;
import com.cookbook.cookbook.model.RecipeModel;
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
    private final RecipeDTOMapper recipeMapper;
    private static final String ERROR_MESSAGE = " does not exist";
    private final EntityCache<String, RecipeModel> recipeCache;


    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeDTOMapper recipeMapper, EntityCache<String, RecipeModel> recipeCache) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
        this.recipeCache = recipeCache;
    }

    public List<RecipeDTO> getRecipes(){
        return recipeRepository.findAll().stream().map(recipeMapper).toList();
    }

    public void addNewRecipe(RecipeModel recipe) {

        if(Objects.equals(recipe.getName(), ""))
            throw new IllegalStateException("Name of recipe is empty");
        if(recipeRepository.findByName(recipe.getName())!=null)
            throw new IllegalStateException("Recipe already exist");

        List<IngredientModel> ingredients = recipe.getIngredients();
        List<IngredientModel> allIngredients = new ArrayList<>();

        for(IngredientModel ingredient : ingredients){
            IngredientModel existingIngredient = ingredientRepository.findByName(ingredient.getName());
            if(existingIngredient != null)
                allIngredients.add(existingIngredient);
            else{
                throw new IllegalStateException("Ingredient with name " + ingredient.getName() + ERROR_MESSAGE);
            }
        }
        recipe.setIngredients(allIngredients);
        recipeRepository.save(recipe);

    }

    public void deleteRecipe(String name) {
        RecipeModel recipe = recipeRepository.findByName(name);
        if(recipe!=null){
            recipeRepository.deleteByName(name);
            recipeCache.remove(name);
        }
        else
            throw new IllegalStateException("Recipe with name " + name + ERROR_MESSAGE);
    }

    public RecipeDTO findByName(String name) {
        RecipeModel recipe = recipeCache.get(name);
        if (recipe == null) {
            recipe = recipeRepository.findByName(name);
        }
        if (recipe != null) {
            recipeCache.put(name, recipe);
            return recipeMapper.apply(recipe);
        }
        else
            throw new IllegalStateException("Recipe with name " + name + ERROR_MESSAGE);
    }

    public void updateRecipe(Long id, String name) {
        Optional<RecipeModel> oldRecipe = recipeRepository.findById(id);
        if (oldRecipe.isPresent()) {
            RecipeModel newRecipe = oldRecipe.get();
            recipeCache.remove(newRecipe.getName());
            newRecipe.setName(name);
            recipeRepository.save(newRecipe);
            recipeCache.put(name, newRecipe);
        }
        else
            throw new IllegalStateException("Recipe with id " + id + ERROR_MESSAGE);
    }
}
