package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.IngredientCommand;
import com.udemy.sfg.recipeapp.converters.IngredientCommandToIngredient;
import com.udemy.sfg.recipeapp.converters.IngredientToIngredientCommand;
import com.udemy.sfg.recipeapp.domain.Ingredient;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import com.udemy.sfg.recipeapp.services.IngredientCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

@Service
@Slf4j
public class IngredientCommandServiceImpl implements IngredientCommandService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeService recipeService;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientCommandServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand
            , RecipeService recipeService, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeService = recipeService;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeService.getById(recipeId);

        if(recipe == null) {
            log.warn("Recipe is not found by id {} ", recipeId);
            return null;
        }

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter( in -> ingredientId.equals(in.getId()))
                .findFirst()
                .orElse(null);
        if(ingredient == null) {
            log.warn("Ingredient is not found by id {} ", ingredientId);
            return null;
        }

        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeService.getById(command.getRecipeId());

        if(recipe == null){
            log.error("Recipe not found for id: {}", command.getRecipeId());
            return new IngredientCommand();
        }
        else {

            // new ingredient case
            if(command.getId() == null) {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.getIngredients().add(ingredient);
            }
            else
            {
                Ingredient ingredientSearched = recipe.getIngredients()
                        .stream()
                        .filter(ingredient -> command.getId().equals(ingredient.getId()))
                        .findFirst()
                        .orElse(null);
                if(ingredientSearched != null){
                    ingredientSearched.setDescription(command.getDescription());
                    ingredientSearched.setAmount(command.getAmount());
                    ingredientSearched.setUnitOfMeasure(unitOfMeasureRepository
                            .findById(command.getUnitOfMeasure().getId())
                            .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
                }
                else {
                    Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                    ingredient.setRecipe(recipe);
                    recipe.getIngredients().add(ingredient);
                }
            }
        }

        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        //to do check for fail
        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                .findFirst()
                .get());
    }

    @Override
    public void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeService.getById(recipeId);

        if(recipe == null){
            throw new RuntimeException("Recipe not found for id: " + recipeId);
        }

        Ingredient ingredient =
                recipe.getIngredients().stream().filter(ingredient1 -> ingredientId.equals(ingredient1.getId()))
                        .findFirst().orElse(null);

        if(ingredient != null) {
            recipe.getIngredients().remove(ingredient);
            ingredient.setRecipe(null);
            recipeService.saveRecipe(recipe);
        }
    }
}
