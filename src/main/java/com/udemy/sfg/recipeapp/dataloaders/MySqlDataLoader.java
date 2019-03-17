package com.udemy.sfg.recipeapp.dataloaders;

import com.udemy.sfg.recipeapp.domain.Category;
import com.udemy.sfg.recipeapp.domain.UnitOfMeasure;
import com.udemy.sfg.recipeapp.repositories.CategoryRepository;
import com.udemy.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
@Slf4j
@Profile({"dev","prod"})
public class MySqlDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;


    public MySqlDataLoader(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(unitOfMeasureRepository.count() == 0) {
            loadUnitsOfMeasure();
        }

        if(categoryRepository.count() == 0) {
            loadCategories();
        }
    }

    private void loadCategories() {
        Category cat1 = new Category();
        cat1.setDescription("American");

        Category cat2 = new Category();
        cat2.setDescription("Italian");

        Category cat3 = new Category();
        cat3.setDescription("Mexican");

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
    }

    private void loadUnitsOfMeasure() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");;

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");

        unitOfMeasureRepository.saveAll(Arrays.asList(uom1, uom2, uom3, uom4, uom5, uom6, uom7, uom8));
    }
}
