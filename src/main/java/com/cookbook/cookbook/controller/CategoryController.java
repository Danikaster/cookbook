package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.service.CategoryService;
import com.cookbook.cookbook.service.CounterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;
    private final CounterService requestCounter;

    public CategoryController(CategoryService categoryService, CounterService requestCounter) {
        this.categoryService = categoryService;
        this.requestCounter = requestCounter;
    }

    @GetMapping()
    public List<CategoryDTO> findAllCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/find/{name}")
    public CategoryDTO findByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }

    @PostMapping("/add")
    public void addNewCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCategory(@RequestParam Long id, @RequestParam String name) {
        categoryService.updateCategory(id, name);
        return null;
    }
}
