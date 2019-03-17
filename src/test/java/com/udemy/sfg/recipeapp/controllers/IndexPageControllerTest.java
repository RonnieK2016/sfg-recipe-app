package com.udemy.sfg.recipeapp.controllers;

import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class IndexPageControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    @InjectMocks
    private IndexPageController indexPageController;

    @BeforeEach
    public void setUp() throws Exception {
        indexPageController = new IndexPageController(recipeService);
    }

    @Test
    public void testMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexPageController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        //given
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        Recipe recipe1 = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);
        recipes.add(recipe1);

        //when
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        assertEquals("index", indexPageController.getIndexPage(model));

        ArgumentCaptor<Set<Recipe>> setArgumentCaptor  = ArgumentCaptor.forClass(Set.class);

        verify(model, times(1)).addAttribute(eq("recipes"), setArgumentCaptor.capture());
        verify(recipeService, times(1)).getAllRecipes();

        assertEquals(2, setArgumentCaptor.getValue().size());
    }
}