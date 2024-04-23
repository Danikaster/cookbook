package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
        return null;
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCategory(@RequestParam Long id, @RequestParam String name) {
        categoryService.updateCategory(id, name);
        return null;
    }
}
