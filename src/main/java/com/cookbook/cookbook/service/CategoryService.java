package com.cookbook.cookbook.service;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.exceptions.BadRequestException;
import com.cookbook.cookbook.exceptions.ResourceNotFoundException;
import com.cookbook.cookbook.exceptions.ServerException;
import com.cookbook.cookbook.mapper.category.CategoryDTOMapper;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.repository.CategoryRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryDTOMapper categoryMapper;
    private static final String ERROR_MESSAGE = " does not exist";
    private final EntityCache<String, Category> categoryCache;

    public CategoryService(CategoryRepository categoryRepository, RecipeRepository recipeRepository, CategoryDTOMapper categoryMapper, EntityCache<String, Category> categoryCache) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.categoryMapper = categoryMapper;
        this.categoryCache = categoryCache;
    }

    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper).toList();
    }

    public void addNewCategory(Category category) {

        if (Objects.equals(category.getName(), "")) {
            throw new ServerException("Name of category is empty");
        }

        List<Recipe> recipes = category.getRecipes();
        List<Recipe> allRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            Recipe existingRecipe = recipeRepository.getById(recipe.getId());
            if (existingRecipe != null) {
                allRecipes.add(existingRecipe);
            } else {
                throw new ResourceNotFoundException("Recipe with id " + recipe.getId() + ERROR_MESSAGE);
            }
        }
        category.setRecipes(allRecipes);

        categoryRepository.save(category);

        for (Recipe recipe : allRecipes) {
            recipe.setCategory(category);
            recipeRepository.save(recipe);
        }
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.getById(id);
        if (category != null) {
            List<Recipe> recipes = category.getRecipes();
            for (Recipe recipe : recipes) {
                recipe.setCategory(null);
                recipeRepository.save(recipe);
            }

            categoryRepository.deleteById(id);
            categoryCache.remove(category.getName());
        } else {
            throw new ResourceNotFoundException("Category with id " + id + ERROR_MESSAGE);
        }
    }

    public CategoryDTO findByName(String name) {
        Category category = categoryCache.get(name);
        if (category == null) {
            category = categoryRepository.findByName(name);
        }
        if (category != null) {
            categoryCache.put(name, category);
            return categoryMapper.apply(category);
        } else {
            throw new ResourceNotFoundException("Category with name " + name + ERROR_MESSAGE);
        }
    }

    public void updateCategory(Long id, String name) {
        Optional<Category> oldCategory = categoryRepository.findById(id);
        if (oldCategory.isPresent()) {
            Category newCategory = oldCategory.get();
            categoryCache.remove(newCategory.getName());
            newCategory.setName(name);
            categoryRepository.save(newCategory);
            categoryCache.put(name, newCategory);
        } else {
            throw new ResourceNotFoundException("Category with id " + id + ERROR_MESSAGE);
        }
    }
}
