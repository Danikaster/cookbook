package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.service.CategoryService;
import com.cookbook.cookbook.service.CounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CounterService requestCounter;

    public CategoryController(CategoryService categoryService, CounterService requestCounter) {
        this.categoryService = categoryService;
        this.requestCounter = requestCounter;
    }

    @GetMapping()
    public List<CategoryDTO> findAllCategories() {
        requestCounter.increment();
        return categoryService.getCategories();
    }

    @GetMapping("/find/{name}")
    public CategoryDTO findByName(@PathVariable String name) {
        requestCounter.increment();
        return categoryService.findByName(name);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        requestCounter.increment();
        categoryService.addNewCategory(category);
        return null;
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        requestCounter.increment();
        categoryService.deleteCategory(name);
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCategory(@RequestParam Long id, @RequestParam String name) {
        requestCounter.increment();
        categoryService.updateCategory(id, name);
        return null;
    }
}
