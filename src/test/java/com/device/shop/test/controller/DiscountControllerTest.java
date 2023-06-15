package com.device.shop.test.controller;

import com.device.shop.controller.DiscountController;
import com.device.shop.entity.Discount;
import com.device.shop.service.DiscountService;
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
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("Test Discount");
        discount.setDescription("Test Description");
        discount.setDiscount_percent("10");

        when(discountService.addNewDiscount(any(Discount.class))).thenReturn(discount);

        mockMvc.perform(post("/discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Test Discount\",\"description\":\"Test Description\",\"discount_percent\":\"10\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Discount"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.discount_percent").value("10"));

        verify(discountService).addNewDiscount(any(Discount.class));
    }

    @Test
    public void testGetAllDiscounts() throws Exception {
        Discount discount1 = new Discount();
        discount1.setId(1L);
        discount1.setName("Discount 1");
        discount1.setDescription("Description 1");
        discount1.setDiscount_percent("10");

        Discount discount2 = new Discount();
        discount2.setId(2L);
        discount2.setName("Discount 2");
        discount2.setDescription("Description 2");
        discount2.setDiscount_percent("20");

        List<Discount> discounts = Arrays.asList(discount1, discount2);

        when(discountService.getAllDiscount()).thenReturn(discounts);

        mockMvc.perform(get("/get/discount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Discount 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].discount_percent").value("10"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Discount 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].discount_percent").value("20"));

        verify(discountService).getAllDiscount();
    }

    @Test
    public void testGetDetailsAboutTheDiscountById() throws Exception {
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("Test Discount");
        discount.setDescription("Test Description");
        discount.setDiscount_percent("10");

        when(discountService.getDiscountById(1L)).thenReturn(discount);

        mockMvc.perform(get("/discount/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Discount"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.discount_percent").value("10"));

        verify(discountService).getDiscountById(1L);
    }

    @Test
    public void testUpdateDiscountInformation() throws Exception {
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("Updated Discount");
        discount.setDescription("Updated Description");
        discount.setDiscount_percent("15");

        when(discountService.updateDiscountById(any(Discount.class), eq(1L))).thenReturn(discount);

        mockMvc.perform(post("/discount/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Discount\",\"description\":\"Updated Description\",\"discount_percent\":\"15\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Discount"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.discount_percent").value("15"));

        verify(discountService).updateDiscountById(any(Discount.class), eq(1L));
    }

    @Test
    public void testDeleteDiscount() throws Exception {
        mockMvc.perform(delete("/discount/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Discount successfully deleted!"));

        verify(discountService).deleteDiscount(1L);
    }

}