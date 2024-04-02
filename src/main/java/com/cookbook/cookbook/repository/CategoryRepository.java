package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    void deleteByName(String name);
}
