package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.domain.Category;

import java.util.Set;

public interface CategoryService {

    Set<Category> getAllCategories();

    Category findById(Long id);
}
