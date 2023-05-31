package com.device.shop.test.service;

import com.device.shop.entity.Product;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.ProductService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAllUser_ReturnsListOfUsers() {
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

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> retrievedList = productService.getAllProducts();
        Assertions.assertEquals(productList, retrievedList);

        verify(productRepository, times(1)).findAll();
    }
}
