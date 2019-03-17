package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.UnitOfMeasureCommand;
import com.udemy.sfg.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.udemy.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import com.udemy.sfg.recipeapp.services.UnitOfMeasureCommandService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureCommandServiceImpl implements UnitOfMeasureCommandService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;


    public UnitOfMeasureCommandServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                           UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomConverter = uomConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> getAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(uomConverter::convert)
                .collect(Collectors.toSet());
    }
}
