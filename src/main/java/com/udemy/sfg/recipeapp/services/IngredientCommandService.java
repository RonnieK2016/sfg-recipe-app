package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.IngredientCommand;

public interface IngredientCommandService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
