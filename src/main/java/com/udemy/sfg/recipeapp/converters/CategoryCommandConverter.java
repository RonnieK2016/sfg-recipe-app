package com.udemy.sfg.recipeapp.converters;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;
import com.udemy.sfg.recipeapp.services.CategoryCommandService;
import com.udemy.sfg.recipeapp.services.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandConverter implements Converter<String, CategoryCommand> {

    private final CategoryCommandService categoryCommandService;

    public CategoryCommandConverter(CategoryCommandService categoryCommandService) {
        this.categoryCommandService = categoryCommandService;
    }

    @Override
    public CategoryCommand convert(String source) {
        return categoryCommandService.findCategoryCommandById(Long.valueOf(source));
    }
}
