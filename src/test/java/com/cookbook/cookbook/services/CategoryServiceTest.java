package com.cookbook.cookbook.services;

import com.cookbook.cookbook.cache.EntityCache;
import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.exceptions.ResourceNotFoundException;
import com.cookbook.cookbook.exceptions.ServerException;
import com.cookbook.cookbook.mapper.category.CategoryDTOMapper;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.repository.CategoryRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import com.cookbook.cookbook.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CategoryDTOMapper categoryMapper;

    @Mock
    private EntityCache<String, Category> categoryCache;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCategories_ReturnsListOfCategories() {
        Category category = new Category();
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryMapper.apply(any())).thenReturn(new CategoryDTO());

        List<CategoryDTO> categories = categoryService.getCategories();

        assertFalse(categories.isEmpty());
    }

    @Test
    void addNewCategory_ThrowsServerException_WhenNameIsEmpty() {
        Category category = new Category();
        category.setName("");

        assertThrows(ServerException.class, () -> categoryService.addNewCategory(category));
    }

    @Test
    void addNewCategory_SavesCategoryToRepository() {
        Category category = new Category();
        category.setName("TestCategory");

        categoryService.addNewCategory(category);

        verify(categoryRepository, times(1)).save(category);
    }


    /*@Test
    void deleteCategory_RemovesCategoryFromRepositoryAndCache() {
        String categoryName = "TestCategory";
        Category category = new Category();
        category.setName(categoryName);

        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        category.setRecipes(Arrays.asList(recipe1, recipe2));

        when(categoryRepository.findByName(categoryName)).thenReturn(category);

        categoryService.deleteCategory(categoryName);

        verify(recipeRepository, times(2)).save(any(Recipe.class));
        verify(categoryRepository, times(1)).deleteByName(categoryName);
        verify(categoryCache, times(1)).remove(categoryName);
    }*/


    /*@Test
    void deleteCategory_ThrowsResourceNotFoundException_WhenCategoryNotFound() {
        String categoryName = "NonExistentCategory";
        when(categoryRepository.findByName(categoryName)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(categoryName));
    }*/

    @Test
    void findByName_ReturnsCategoryDTO_WhenCategoryFoundInCache() {
        String categoryName = "TestCategory";
        Category category = new Category();
        category.setName(categoryName);
        when(categoryCache.get(categoryName)).thenReturn(category);
        when(categoryMapper.apply(category)).thenReturn(new CategoryDTO());

        CategoryDTO foundCategory = categoryService.findByName(categoryName);

        assertNotNull(foundCategory);
    }

    @Test
    void findByName_ThrowsResourceNotFoundException_WhenCategoryNotFound() {
        String categoryName = "NonExistentCategory";
        when(categoryCache.get(categoryName)).thenReturn(null);
        when(categoryRepository.findByName(categoryName)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.findByName(categoryName));
    }

    @Test
    void updateCategory_UpdatesCategoryName_WhenCategoryExists() {
        Long categoryId = 1L;
        String newName = "NewName";
        Category oldCategory = new Category();
        oldCategory.setId(categoryId);
        oldCategory.setName("OldName");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(oldCategory));

        categoryService.updateCategory(categoryId, newName);

        assertEquals(newName, oldCategory.getName());
    }

    @Test
    void updateCategory_ThrowsResourceNotFoundException_WhenCategoryNotFound() {
        Long nonExistentId = 999L;
        String newName = "NewName";
        when(categoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(nonExistentId, newName));
    }
}
