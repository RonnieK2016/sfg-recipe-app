package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;

public interface RecipeCommandService {

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findRecipeCommandById(Long id);
}
