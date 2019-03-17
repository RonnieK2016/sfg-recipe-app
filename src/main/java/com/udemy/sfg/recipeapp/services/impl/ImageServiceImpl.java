package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.services.ImageService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeService recipeService;

    public ImageServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeService.getById(recipeId);

        if(recipe == null) {
            throw new RuntimeException("Cannot find recipe by id " + recipeId);
        }
        else {
            try {
                Byte[] bytes = new Byte[file.getBytes().length];
                int i = 0;
                for(byte b : file.getBytes()) {
                    bytes[i++] = b;
                }

                recipe.setImage(bytes);

                recipeService.saveRecipe(recipe);
            }
            catch (IOException e) {
                log.error("Error during file store ", e);
            }
        }
    }
}
