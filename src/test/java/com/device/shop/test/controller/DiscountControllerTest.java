package com.device.shop.test.controller;

import com.device.shop.controller.DiscountController;
import com.device.shop.model.DiscountDTO;
import com.device.shop.service.DiscountService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DiscountControllerTest {


    private MockMvc mockMvc;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private DiscountController discountController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(discountController).build();
    }

    @Test
    public void testAddDiscount() throws Exception {
        DiscountDTO discount = DiscountDTO.builder()
                .id(1L)
                .name("Test Discount")
                .description("Test Description")
                .discountPercent("10")
                .build();

        when(discountService.addNewDiscount(any())).thenReturn(discount);

        mockMvc.perform(post("/discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Test Discount\",\"description\":\"Test Description\",\"discountPercent\":\"10\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Discount"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.discountPercent").value("10"));

        verify(discountService).addNewDiscount(any());
    }

    //toDo testAddDiscount_BadRequestException

    @Test
    public void testGetAllDiscounts() throws Exception {
        DiscountDTO discount1 = DiscountDTO.builder()
                .id(1L)
                .name("Discount 1")
                .description("Description 1")
                .discountPercent("10")
                .build();

        DiscountDTO discount2 = DiscountDTO.builder()
                .id(2L)
                .name("Discount 2")
                .description("Description 2")
                .discountPercent("20")
                .build();

        List<DiscountDTO> discounts = Arrays.asList(discount1, discount2);

        when(discountService.getAllDiscounts()).thenReturn(discounts);

        mockMvc.perform(get("/discount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Discount 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].discountPercent").value("10"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Discount 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].discountPercent").value("20"));

        verify(discountService).getAllDiscounts();
    }

    // toDo testGetAllDiscounts_EntityNotFoundException

    @Test
    public void testGetDetailsAboutTheDiscountById() throws Exception {
        DiscountDTO discountDTO = DiscountDTO.builder()
                .id(1L)
                .name("Test Discount")
                .description("Test Description")
                .discountPercent("10")
                .build();

        when(discountService.getDiscountById(1L)).thenReturn(discountDTO);

        mockMvc.perform(get("/discount/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Discount"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.discountPercent").value("10"));

        verify(discountService).getDiscountById(1L);
    }

    //toDo testGetDetailsAboutTheDiscountById_EntityNotFoundException

    @Test
    public void testUpdateDiscountInformation() throws Exception {
        DiscountDTO discountDTO = DiscountDTO.builder()
                .id(1L)
                .name("Updated Discount")
                .description("Updated Description")
                .discountPercent("15")
                .build();

        when(discountService.updateDiscountById(eq(1L), any(DiscountDTO.class))).thenReturn(discountDTO);

        mockMvc.perform(put("/discount/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(discountDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Discount"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.discountPercent").value("15"));

        verify(discountService).updateDiscountById(eq(1L), any(DiscountDTO.class));
    }

    //toDo testUpdateDiscountInformation_EntityNotFoundException
    //toDo testGetDetailsAboutTheDiscountById_BadRequestException

    @Test
    public void testDeleteDiscount() throws Exception {
        mockMvc.perform(delete("/discount/1")).andExpect(status().isOk()).andExpect(content().string("Discount successfully deleted!"));

        verify(discountService).deleteDiscount(1L);
    }

    //toDo testDeleteDiscount_EntityNotFoundException

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}