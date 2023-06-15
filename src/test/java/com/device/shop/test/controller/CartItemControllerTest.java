package com.device.shop.test.controller;

import com.device.shop.controller.CartItemController;
import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;
import com.device.shop.exception.ExceptionController;
import com.device.shop.service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CartItemControllerTest {

    @Mock
    CartItemService cartItemService;
    @InjectMocks
    CartItemController cartItemController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        cartItemController = new CartItemController(cartItemService);
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    void testAddingProductToTheCartById() throws Exception {
        Long productId = 1L;
        CartItem cartItem = new CartItem();
        ResponseEntity<CartItem> expectedResponse = new ResponseEntity<>(cartItem, HttpStatus.OK);

        when(cartItemService.addToCart(productId)).thenReturn(cartItem);

        mockMvc.perform(
                        post("/cart/{id}/items", productId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(cartItem.getId()))
                .andExpect(jsonPath("$.quantity").value(cartItem.getQuantity()));


        ResponseEntity<CartItem> actualResponse = cartItemController.addingProductToTheCartById(productId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(cartItemService, times(2)).addToCart(productId);
    }

    @Test
    public void testGetProductsInCart() throws Exception {
        Long cartId = 1L;
        List<Product> products = new ArrayList<>();

        when(cartItemService.getProductsInCart(cartId)).thenReturn(products);

        mockMvc.perform(get("/cart/{id}/products", cartId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(cartItemService, times(1)).getProductsInCart(cartId);
    }

    @Test
    public void testDeleteTheProductsById() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/delete/card/{id}/items", productId))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).deleteTheProduct(productId);
    }

    @Test
    public void deleteAllProducts_ShouldReturnOk() throws Exception {
        Long cartId = 1L;

        mockMvc.perform(delete("/delete/all/products/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All products removed from the cart"));

        verify(cartItemService).removeAllProductsFromCart(cartId);
    }

}