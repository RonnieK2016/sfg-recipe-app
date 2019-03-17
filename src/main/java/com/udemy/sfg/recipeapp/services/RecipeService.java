package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipes();

    Recipe getById(Long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteById(Long id);
}
