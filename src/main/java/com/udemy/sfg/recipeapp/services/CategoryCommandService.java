package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;

import java.util.Set;

public interface CategoryCommandService {

    Set<CategoryCommand> getAllCategoryCommands();

    CategoryCommand findCategoryCommandById(Long id);
}
