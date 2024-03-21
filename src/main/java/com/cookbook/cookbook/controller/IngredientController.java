package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.IngredientModel;
import com.cookbook.cookbook.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping()
    public List<IngredientModel> findAllIngredients() {
        return ingredientService.getIngredients();
    }

    @GetMapping("/find/{name}")
    public IngredientModel findByName (@PathVariable String name) {
        return ingredientService.findByName(name);
    }

    @PostMapping("/add")
    public void addIngredient(@RequestBody IngredientModel ingredient){
        ingredientService.addNewIngredient(ingredient);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteIngredient(@PathVariable String name){
        ingredientService.deleteIngredient(name);
    }

    @PatchMapping("/update")
    public void updateIngredient(@RequestParam Long id, @RequestParam String name){
        ingredientService.updateIngredient(id, name);
    }
}
