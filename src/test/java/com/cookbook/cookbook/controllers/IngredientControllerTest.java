package com.cookbook.cookbook.controllers;

import com.cookbook.cookbook.controller.IngredientController;
import com.cookbook.cookbook.dto.ingredient.IngredientDTO;
import com.cookbook.cookbook.model.Ingredient;
import com.cookbook.cookbook.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IngredientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testFindAllIngredients() throws Exception {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        // Populate ingredientDTOList with test data

        when(ingredientService.getIngredients()).thenReturn(ingredientDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).getIngredients();
        verifyNoMoreInteractions(ingredientService);
    }

    @Test
    public void testFindByName() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO();
        // Set up ingredientDTO with test data

        when(ingredientService.findByName(anyString())).thenReturn(ingredientDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredients/find/{name}", "TestIngredient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).findByName(anyString());
        verifyNoMoreInteractions(ingredientService);
    }

    @Test
    public void testAddIngredient() throws Exception {
        Ingredient ingredient = new Ingredient();
        // Set up ingredient with test data

        mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ingredient)))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).addNewIngredient(any(Ingredient.class));
        verifyNoMoreInteractions(ingredientService);
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ingredients/delete/{name}", "TestIngredient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).deleteIngredient(anyString());
        verifyNoMoreInteractions(ingredientService);
    }

    @Test
    public void testUpdateIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ingredients/update")
                        .param("id", "1")
                        .param("name", "UpdatedName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).updateIngredient(anyLong(), anyString());
        verifyNoMoreInteractions(ingredientService);
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
