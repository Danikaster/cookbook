package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.service.CounterService;
import com.cookbook.cookbook.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    private final CounterService requestCounter;

    public IngredientController(IngredientService ingredientService, CounterService requestCounter) {
        this.ingredientService = ingredientService;
        this.requestCounter = requestCounter;
    }

    @GetMapping()
    public List<IngredientDTO> findAllIngredients() {
        requestCounter.increment();
        return ingredientService.getIngredients();
    }

    @GetMapping("/find/{name}")
    public IngredientDTO findByName(@PathVariable String name) {
        requestCounter.increment();
        return ingredientService.findByName(name);
    }

    @PostMapping("/add")
    public void addIngredient(@RequestBody Ingredient ingredient) {
        requestCounter.increment();
        ingredientService.addNewIngredient(ingredient);
    }

    @PostMapping("/addIngredients")
    public void addIngredients(@RequestBody Ingredient[] ingredients) {
        requestCounter.increment();
        Stream.of(ingredients).forEach(ingredientService::addNewIngredient);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteIngredient(@PathVariable String name) {
        requestCounter.increment();
        ingredientService.deleteIngredient(name);
    }

    @PutMapping("/update")
    public void updateIngredient(@RequestParam Long id, @RequestParam String name) {
        requestCounter.increment();
        ingredientService.updateIngredient(id, name);
    }
}
