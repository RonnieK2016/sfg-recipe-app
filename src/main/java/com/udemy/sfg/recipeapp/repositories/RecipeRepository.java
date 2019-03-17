package com.udemy.sfg.recipeapp.repositories;

import com.udemy.sfg.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
