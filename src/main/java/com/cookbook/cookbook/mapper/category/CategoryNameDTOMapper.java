package com.cookbook.cookbook.mapper.category;

import com.cookbook.cookbook.dto.category.CategoryNameDTO;
import com.cookbook.cookbook.model.Category;

import java.util.function.Function;

public class CategoryNameDTOMapper implements Function<Category, CategoryNameDTO> {
    @Override
    public CategoryNameDTO apply(Category category) {
        return new CategoryNameDTO(category.getId(), category.getName());
    }

}
