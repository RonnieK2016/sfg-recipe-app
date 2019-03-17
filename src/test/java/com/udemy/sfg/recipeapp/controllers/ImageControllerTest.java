package com.udemy.sfg.recipeapp.controllers;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.services.ImageService;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private ImageService imageService;
    @Mock
    private RecipeCommandService recipeCommandService;

    private ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        imageController = new ImageController(recipeCommandService, imageService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(imageController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    void showUploadForm() throws Exception {
        when(recipeCommandService.findRecipeCommandById(anyLong())).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeCommandService).findRecipeCommandById(anyLong());
    }

    @Test
    void handleImagePost() throws Exception {

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));

        verify(imageService).saveImageFile(anyLong(), any(MultipartFile.class));
    }

    @Test
    void renderImageFromDb() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        byte[] image = "FAKE IMAGE TEXT".getBytes();
        Byte[] bytesObject = new Byte[image.length];

        int i = 0;
        for(byte b : image) {
            bytesObject[i++] = b;
        }

        recipeCommand.setImage(bytesObject);

        when(recipeCommandService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] returnedImage = response.getContentAsByteArray();

        assertEquals(image.length, returnedImage.length);
    }

    @Test
    void testNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/asdf/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400"));
    }
}