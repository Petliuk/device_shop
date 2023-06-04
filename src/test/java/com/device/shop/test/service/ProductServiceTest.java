package com.device.shop.test.service;

import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductServiceTest {
    @Autowired
    private MockMvc mockMvc;
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
        assertEquals(productList, retrievedList);

        verify(productRepository, times(1)).findAll();
    }


    @Test
    public void testSave_InvalidCSVFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid CSV".getBytes());

        ResultMatcher expectedStatus = status().isBadRequest();

        mockMvc.perform(multipart("/upload")
                        .file(file))
                .andExpect(expectedStatus);
    }

    @Test
    void save_InvalidCSVFile_ThrowsBadRequestException() {
        ProductRepository productRepository = mock(ProductRepository.class);
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "invalid csv".getBytes());

        assertThrows(BadRequestException.class, () -> productService.save(file));
        verifyNoInteractions(productRepository);
    }

}

