package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.aop.AspectAnnotation;
import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @AspectAnnotation
    @GetMapping()
    public List<CategoryDTO> findAllCategories() {
        return categoryService.getCategories();
    }

    @AspectAnnotation
    @GetMapping("/find/{name}")
    public CategoryDTO findByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }

    @AspectAnnotation
    @PostMapping("/add")
    public void addCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
    }

    @AspectAnnotation
    @DeleteMapping("/delete/{name}")
    public void deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
    }

    @AspectAnnotation
    @PutMapping("/update")
    public void updateCategory(@RequestParam Long id, @RequestParam String name) {
        categoryService.updateCategory(id, name);
    }
}
