package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.RecipeModel;
import com.cookbook.cookbook.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping()
    public List<RecipeModel> findAllRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping("/find/{name}")
    public RecipeModel findByName (@PathVariable String name) {
        return recipeService.findByName(name);
    }

    @PostMapping("/add")
    public void addRecipe(@RequestBody RecipeModel recipe){
        recipeService.addNewRecipe(recipe);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteRecipe(@PathVariable String name){
        recipeService.deleteRecipe(name);
    }

    @PatchMapping("/update")
    public void updateRecipe(@RequestParam Long id, @RequestParam String name){
        recipeService.updateRecipe(id, name);
    }
}