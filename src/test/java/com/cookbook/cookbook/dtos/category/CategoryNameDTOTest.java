package com.cookbook.cookbook.dtos.category;

import com.cookbook.cookbook.dto.category.CategoryNameDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryNameDTOTest {

    @Test
    public void testGettersAndSetters() {
        CategoryNameDTO category = new CategoryNameDTO();
        category.setId(1);
        category.setName("Test");

        assertEquals(1, category.getId());
        assertEquals("Test", category.getName());
    }

    @Test
    public void testToString() {
        CategoryNameDTO category = new CategoryNameDTO(1, "Test");

        assertEquals("CategoryNameDTO{id=1, name='Test'}", category.toString());
    }

    @Test
    public void testConstructorWithName() {
        CategoryNameDTO category = new CategoryNameDTO("Test");

        assertEquals("Test", category.getName());
        assertEquals(0, category.getId()); // ID should default to 0 if not set explicitly
    }

    @Test
    public void testConstructorWithIdAndName() {
        CategoryNameDTO category = new CategoryNameDTO(1, "Test");

        assertEquals(1, category.getId());
        assertEquals("Test", category.getName());
    }
}

