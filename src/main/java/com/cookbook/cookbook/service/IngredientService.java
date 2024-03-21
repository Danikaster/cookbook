package com.cookbook.cookbook.service;


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


    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientModel> getIngredients(){
        return ingredientRepository.findAll();
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
            //ingredientRepository.delete(ingredient);
            ingredientRepository.deleteByName(name);
        }
        else
            throw new IllegalStateException("Ingredient with name " + name + " does not exist");
    }

    public IngredientModel findByName(String name) {
        IngredientModel ingredient = ingredientRepository.findByName(name);
        if (ingredient != null) {
            return ingredient;
        }
        else
            throw new IllegalStateException("Ingredient with name " + name + " does not exist");
    }

    public void updateIngredient(Long id, String name) {
        Optional<IngredientModel> oldIngredient = ingredientRepository.findById(id);
        if (oldIngredient.isPresent()) {
            IngredientModel newIngredient = oldIngredient.get();
            newIngredient.setName(name);
            ingredientRepository.save(newIngredient);
        }
        else
            throw new IllegalStateException("Ingredient with id " + id + " does not exist");
    }
}
