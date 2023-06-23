package com.device.shop.test.controller;

import com.device.shop.controller.ProductController;
import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.exception.ExceptionController;
import com.device.shop.model.ProductDTO;
import com.device.shop.service.impl.ProductImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {


    @Mock
    private ProductImpl productService;
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
    public void testGetAllProducts() throws Exception {
        ProductDTO product1 = ProductDTO.builder()
                .id(1L)
                .name("phone")
                .description("*")
                .sku("one")
                .build();
        ProductDTO product2 = ProductDTO.builder()
                .id(2L)
                .name("laptop")
                .description("*")
                .sku("two")
                .build();
        List<ProductDTO> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("phone"))
                .andExpect(jsonPath("$[0].description").value("*"))
                .andExpect(jsonPath("$[0].sku").value("one"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("laptop"))
                .andExpect(jsonPath("$[1].description").value("*"))
                .andExpect(jsonPath("$[1].sku").value("two"));
    }

    @Test
    public void testGetProductById() throws Exception {
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .name("Назва товару")
                .description("Опис товару")
                .sku("ABC123")
                .price(10.99)
                .build();

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Назва товару"))
                .andExpect(jsonPath("$.description").value("Опис товару"))
                .andExpect(jsonPath("$.sku").value("ABC123"))
                .andExpect(jsonPath("$.price").value(10.99));
    }

    @Test
    public void testGetProductById_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException()).when(productService).getProductById(anyLong());

        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeleteProductsById() throws Exception {
        mockMvc.perform(delete("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Product successfully deleted!"));
    }



    @Test
    public void testDeleteProduct_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/product/{id}", 1L))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testUpdateProductById() throws Exception {
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .sku("123456")
                .price(20.0)
                .build();

        when(productService.updateProduct(any(ProductDTO.class), anyLong())).thenReturn(product);

        mockMvc.perform(post("/product/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.sku").value("123456"))
                .andExpect(jsonPath("$.price").value(20.0));

        verify(productService, times(1)).updateProduct(any(ProductDTO.class), anyLong());
    }


    @Test
    public void testUpdateProductById_EntityNotFound() throws Exception {
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .sku("123456")
                .price(20.0)
                .build();

        when(productService.updateProduct(any(ProductDTO.class), anyLong()))
                .thenThrow(new EntityNotFoundException("Product with id 1 not found"));

        mockMvc.perform(post("/product/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).updateProduct(any(ProductDTO.class), anyLong());
    }

    @Test
    public void testGetProductByName() throws Exception {
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .price(10.0)
                .build();

        when(productService.getProductByName(anyString())).thenReturn(product);

        mockMvc.perform(get("/name/{name}", product.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .sku("TEST_SKU")
                .price(9.99)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .deletedAt(null)
                .build();

        Long categoryId = 1L;
        List<Product> products = Arrays.asList(product, product);

        List<ProductDTO> productDTOs = products.stream()
                .map(p -> ProductDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .sku(p.getSku())
                        .price(p.getPrice())
                        .build())
                .collect(Collectors.toList());

        when(productService.getProductsByCategory(categoryId)).thenReturn(productDTOs);

        mockMvc.perform(get("/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(products.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(products.get(1).getId()));

        verify(productService).getProductsByCategory(categoryId);
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(10.0)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        when(productService.addProducts(any(ProductDTO.class))).thenReturn(ResponseEntity.ok(productDTO));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, "test data" .getBytes());

        mockMvc.perform(multipart("/upload/file")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Uploaded the file successfully: test.csv")));
    }

    @Test
    public void testSave_InvalidCSVFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "invalid csv" .getBytes());

        doThrow(new BadRequestException(" ")).when(productService).save(file);

        mockMvc.perform(multipart("/upload/file")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}