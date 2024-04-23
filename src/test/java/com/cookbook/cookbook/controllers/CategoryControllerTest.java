package com.cookbook.cookbook.controllers;

import com.cookbook.cookbook.controller.CategoryController;
import com.cookbook.cookbook.dto.category.CategoryDTO;
import com.cookbook.cookbook.model.Category;
import com.cookbook.cookbook.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllCategories_ReturnsListOfCategories() {
        // Arrange
        List<CategoryDTO> categories = new ArrayList<>();
        when(categoryService.getCategories()).thenReturn(categories);

        // Act
        List<CategoryDTO> result = categoryController.findAllCategories();

        // Assert
        assertEquals(categories, result);
    }

    @Test
    void findByName_ReturnsCategoryDTO_WhenCategoryExists() {
        // Arrange
        String categoryName = "TestCategory";
        CategoryDTO categoryDTO = new CategoryDTO();
        when(categoryService.findByName(categoryName)).thenReturn(categoryDTO);

        // Act
        CategoryDTO result = categoryController.findByName(categoryName);

        // Assert
        assertEquals(categoryDTO, result);
    }

    /*@Test
    void addCategory_ReturnsHttpStatusOk() {
        // Arrange
        Category category = new Category();

        // Act
        ResponseEntity<Void> responseEntity = categoryController.addCategory(category);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(categoryService, times(1)).addNewCategory(category);
    }*/

    /*@Test
    void deleteCategory_ReturnsHttpStatusOk() {
        // Arrange
        String categoryName = "TestCategory";

        // Act
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(categoryName);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(categoryService, times(1)).deleteCategory(categoryName);
    }*/

    /*@Test
    void updateCategory_ReturnsHttpStatusOk() {
        // Arrange
        Long categoryId = 1L;
        String newName = "NewName";

        // Act
        ResponseEntity<Void> responseEntity = categoryController.updateCategory(categoryId, newName);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(categoryService, times(1)).updateCategory(categoryId, newName);
    }*/
}
