package com.device.shop.test.controller;

import com.device.shop.controller.ProductController;
import com.device.shop.entity.Product;
import com.device.shop.exception.ExceptionController;
import com.device.shop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    private ProductController productController;

    @BeforeEach
    public void setup() {
        productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    public void testGetAllProducts() throws Exception{
       Product product1 = Product.builder()
               .name("phone")
               .description("*")
               .sku("one")
               .build();
       Product product2 = Product.builder()
               .name("laptop")
               .description("*")
               .sku("one")
               .build();
       List<Product> productList = Arrays.asList(product1, product2);

       when(productService.getAllProducts()).thenReturn(productList);
        mockMvc.perform(get("/allProducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("phone"))
                .andExpect(jsonPath("$[0].description").value("*"))
                .andExpect(jsonPath("$[0].sku").value("one"))
                .andExpect(jsonPath("$[1].name").value("laptop"))
                .andExpect(jsonPath("$[1].description").value("*"))
                .andExpect(jsonPath("$[1].sku").value("one"));
    }
    }


