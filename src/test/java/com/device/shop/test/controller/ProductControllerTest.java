package com.device.shop.test.controller;

import com.device.shop.controller.ProductController;
import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.exception.ExceptionController;
import com.device.shop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
       mockMvc.perform(get("/getAllProducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("phone"))
                .andExpect(jsonPath("$[0].description").value("*"))
                .andExpect(jsonPath("$[0].sku").value("one"))
                .andExpect(jsonPath("$[1].name").value("laptop"))
                .andExpect(jsonPath("$[1].description").value("*"))
                .andExpect(jsonPath("$[1].sku").value("one"));
    }

    @Test
    public void testUploadFile() throws Exception {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.csv");

        ResponseEntity<String> response = productController.uploadFile(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Uploaded the file successfully: test.csv", response.getBody());
        verify(productService, times(1)).save(file);
    }

    @Test
    public void testSave_InvalidCSVFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "invalid csv".getBytes());

        doThrow(new BadRequestException(" ")).when(productService).save(file);

        mockMvc.perform(multipart("/upload")
                        .file(file)
                .contentType(MediaType.APPLICATION_JSON));
    }
}