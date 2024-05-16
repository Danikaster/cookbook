package com.cookbook.cookbook.service;


import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.exceptions.BadRequestException;
import com.cookbook.cookbook.exceptions.ResourceNotFoundException;
import com.cookbook.cookbook.mapper.ingredient.IngredientDTOMapper;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.repository.IngredientRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientDTOMapper ingredientMapper;
    private static final String ERROR_MESSAGE = " does not exist";
    private final EntityCache<String, Ingredient> ingredientCache;
    private final RecipeRepository recipeRepository;

    public IngredientService(IngredientRepository ingredientRepository, IngredientDTOMapper ingredientMapper, EntityCache<String, Ingredient> ingredientCache, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientCache = ingredientCache;
        this.recipeRepository = recipeRepository;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredientRepository.findAll().stream().map(ingredientMapper).toList();
    }

    public void addNewIngredient(Ingredient ingredient) {

        if (Objects.equals(ingredient.getName(), "")) {
            throw new BadRequestException("Name of ingredient is empty");
        }
        if (ingredientRepository.findByName(ingredient.getName()) != null) {
            throw new BadRequestException("Ingredient already exist");
        }

        ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.getById(id);
        if (ingredient != null) {
            ingredientRepository.deleteById(id);
            ingredientCache.remove(ingredient.getName());
        } else {
            throw new ResourceNotFoundException("Ingredient with id " + id + ERROR_MESSAGE);
        }
    }

    public IngredientDTO findByName(String name) {
        Ingredient ingredient = ingredientCache.get(name);
        if (ingredient == null) {
            ingredient = ingredientRepository.findByName(name);
        }
        if (ingredient != null) {
            ingredientCache.put(name, ingredient);
            return ingredientMapper.apply(ingredient);
        } else {
            throw new ResourceNotFoundException("Ingredient with name " + name + ERROR_MESSAGE);
        }
    }

    public void updateIngredient(Long id, String name) {
        Optional<Ingredient> oldIngredient = ingredientRepository.findById(id);
        if (oldIngredient.isPresent()) {
            Ingredient newIngredient = oldIngredient.get();
            ingredientCache.remove(newIngredient.getName());
            newIngredient.setName(name);
            ingredientRepository.save(newIngredient);
            ingredientCache.put(name, newIngredient);
        } else {
            throw new ResourceNotFoundException("Ingredient with id " + id + ERROR_MESSAGE);
        }
    }

}
