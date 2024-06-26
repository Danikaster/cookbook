package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.recipe.RecipeDTO;
import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.service.CounterService;
import com.cookbook.cookbook.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;
    private final CounterService requestCounter;

    public RecipeController(RecipeService recipeService, CounterService requestCounter) {
        this.recipeService = recipeService;
        this.requestCounter = requestCounter;
    }

    @GetMapping()
    public List<RecipeDTO> findAllRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping("/find/{name}")
    public RecipeDTO findByName(@PathVariable String name) {
        return recipeService.findByName(name);
    }

    @PostMapping("/add")
    public void addRecipe(@RequestBody Recipe recipe) {
        recipeService.addNewRecipe(recipe);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    @PutMapping("/update")
    public void updateRecipe(@RequestParam Long id, @RequestParam String name, @RequestParam String category) {
        recipeService.updateRecipe(id, name, category);
    }
}
