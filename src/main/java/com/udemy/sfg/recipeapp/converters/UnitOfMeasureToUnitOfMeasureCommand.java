package com.udemy.sfg.recipeapp.converters;

import com.udemy.sfg.recipeapp.commands.UnitOfMeasureCommand;
import com.udemy.sfg.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    @Nullable
    @Synchronized
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source != null) {
            final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
            unitOfMeasureCommand.setId(source.getId());
            unitOfMeasureCommand.setDescription(source.getDescription());
            return unitOfMeasureCommand;
        }
        return null;
    }
}
