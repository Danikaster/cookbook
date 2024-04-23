package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.service.CounterService;
import com.cookbook.cookbook.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final CounterService requestCounter;

    public RecipeController(RecipeService recipeService, CounterService requestCounter) {
        this.recipeService = recipeService;
        this.requestCounter = requestCounter;
    }

    @GetMapping()
    public List<RecipeDTO> findAllRecipes() {
        requestCounter.increment();
        return recipeService.getRecipes();
    }

    @GetMapping("/find/{name}")
    public RecipeDTO findByName(@PathVariable String name) {
        requestCounter.increment();
        return recipeService.findByName(name);
    }

    @PostMapping("/add")
    public void addRecipe(@RequestBody Recipe recipe) {
        requestCounter.increment();
        recipeService.addNewRecipe(recipe);
    }


    @DeleteMapping("/delete/{name}")
    public void deleteRecipe(@PathVariable String name) {
        requestCounter.increment();
        recipeService.deleteRecipe(name);
    }

    @PutMapping("/update")
    public void updateRecipe(@RequestParam Long id, @RequestParam String name) {
        requestCounter.increment();
        recipeService.updateRecipe(id, name);
    }
}
