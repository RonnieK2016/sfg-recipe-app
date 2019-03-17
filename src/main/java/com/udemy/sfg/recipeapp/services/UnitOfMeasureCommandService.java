package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureCommandService {
    Set<UnitOfMeasureCommand> getAllUoms();
}
