package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.service.CounterService;
import com.cookbook.cookbook.service.IngredientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {
    private final IngredientService ingredientService;
    private final CounterService requestCounter;

    public IngredientController(IngredientService ingredientService, CounterService requestCounter) {
        this.ingredientService = ingredientService;
        this.requestCounter = requestCounter;
    }

    @GetMapping()
    public List<IngredientDTO> findAllIngredients() {
        return ingredientService.getIngredients();
    }

    @GetMapping("/find/{name}")
    public IngredientDTO findByName(@PathVariable String name) {
        return ingredientService.findByName(name);
    }

    @PostMapping("/add")
    public void addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addNewIngredient(ingredient);
    }

    @PostMapping("/addIngredients")
    public void addIngredients(@RequestBody Ingredient[] ingredients) {
        Stream.of(ingredients).forEach(ingredientService::addNewIngredient);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }

    @PutMapping("/update")
    public void updateIngredient(@RequestParam Long id, @RequestParam String name) {
        ingredientService.updateIngredient(id, name);
    }
}
