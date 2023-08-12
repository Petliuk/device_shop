package com.device.shop.test.controller;

import com.device.shop.controller.CartItemController;
import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.service.impl.CartItemServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CartItemControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CartItemServiceImpl cartItemService;

    private CartItemController cartItemController;

/*    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cartItemController = new CartItemController(cartItemService);
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }*/

  /*  @Test
    void testAddProductToCart() throws Exception {
        Long sessionId = 1L;
        Long productId = 2L;
        Long cartItemId = 3L;
        Long quantity = 1L;

        CartItemDTO expectedResponse = CartItemDTO.builder()
                .id(cartItemId)
                .quantity(quantity)
                .build();

        when(cartItemService.addToCart(eq(sessionId), any(CartItemDTO.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/cart/{id}/items", sessionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": " + productId + ", \"quantity\": " + quantity + "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(cartItemId))
                .andExpect(jsonPath("$.quantity").value(quantity));

        verify(cartItemService, times(1)).addToCart(eq(sessionId), any(CartItemDTO.class));
    }

    //toDo testAddProduct_BadRequestException

    @Test
    void testGetProductsInCart() throws Exception {
        Long cartId = 1L;

        List<ProductDTO> products = new ArrayList<>();
        products.add(ProductDTO.builder().id(1L).name("Product 1").build());
        products.add(ProductDTO.builder().id(2L).name("Product 2").build());

        when(cartItemService.getProductsInCart(eq(cartId))).thenReturn(products);

        mockMvc.perform(get("/cart/{id}/items", cartId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Product 2"));

        verify(cartItemService, times(1)).getProductsInCart(eq(cartId));
    }*/

    //toDo testGetProductsInCart_EntityNotFoundException

  /*  @Test
    void testDeleteProductById() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/cart/items/{id}", productId))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).deleteTheProduct(productId);
    }*/

    //toDo testDeleteProductById_EntityNotFoundException

    @Test
    void testDeleteAllProducts() throws Exception {
        Long cartId = 1L;

        mockMvc.perform(delete("/cart/{cartId}/items", cartId))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).removeAllProductsFromCart(cartId);
    }

    //toDo testDeleteAllProducts_EntityNotFoundException

}
