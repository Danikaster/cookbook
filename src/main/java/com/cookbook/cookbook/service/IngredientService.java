package com.cookbook.cookbook.service;


import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.mapper.ingredient.IngredientDTOMapper;
import com.cookbook.cookbook.model.IngredientModel;
import com.cookbook.cookbook.repository.IngredientRepository;
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
    private final EntityCache<String, IngredientModel> ingredientCache;

    public IngredientService(IngredientRepository ingredientRepository, IngredientDTOMapper ingredientMapper, EntityCache<String, IngredientModel> ingredientCache) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientCache = ingredientCache;
    }

    public List<IngredientDTO> getIngredients(){
        return ingredientRepository.findAll().stream().map(ingredientMapper).toList();
    }

    public void addNewIngredient(IngredientModel ingredient) {

        if(Objects.equals(ingredient.getName(), ""))
            throw new IllegalStateException("Name of ingredient is empty");
        if(ingredientRepository.findByName(ingredient.getName())!=null)
            throw new IllegalStateException("Ingredient already exist");

        ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(String name) {
        IngredientModel ingredient = ingredientRepository.findByName(name);
        if(ingredient!=null){
            ingredientRepository.deleteByName(name);
            ingredientCache.remove(name);
        }
        else
            throw new IllegalStateException("Ingredient with name " + name + ERROR_MESSAGE);
    }

    public IngredientDTO findByName(String name) {
        IngredientModel ingredient = ingredientCache.get(name);
        if (ingredient == null) {
            ingredient = ingredientRepository.findByName(name);
        }
        if (ingredient != null) {
            ingredientCache.put(name, ingredient);
            return ingredientMapper.apply(ingredient);
        } else {
            throw new IllegalStateException("Ingredient with name " + name + ERROR_MESSAGE);
        }
    }

    public void updateIngredient(Long id, String name) {
        Optional<IngredientModel> oldIngredient = ingredientRepository.findById(id);
        if (oldIngredient.isPresent()) {
            IngredientModel newIngredient = oldIngredient.get();
            ingredientCache.remove(newIngredient.getName());
            newIngredient.setName(name);
            ingredientRepository.save(newIngredient);
            ingredientCache.put(name, newIngredient);
        }
        else
            throw new IllegalStateException("Ingredient with id " + id + ERROR_MESSAGE);
    }
}
