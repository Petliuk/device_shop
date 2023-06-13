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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
    public void testGetProductById() throws Exception {
      Product product = Product.builder()
              .id(1L)
              .name("Назва товару")
              .description("Опис товару")
              .sku("ABC123")
              .price(10.99)
              .build();

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/{id}", 1L))
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

        mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProductsById () throws Exception{
        mockMvc.perform(delete("/delete/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Product successfully deleted!"));
    }

    @Test
    public void testDeleteProduct_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/delete/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProductById() throws BadRequestException {
        Long productId = 1L;
        Product updatedProduct = new Product();

        when(productService.updateProduct(updatedProduct, productId)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProductsById(productId, updatedProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(updatedProduct, productId);
    }

    @Test
    public void testUpdateProductById_EntityNotFoundException() throws BadRequestException {
        Long productId = 1L;
        Product updatedProduct = new Product();

        when(productService.updateProduct(updatedProduct, productId)).thenThrow(new EntityNotFoundException("Product not found"));

        Throwable exception = assertThrows(EntityNotFoundException.class, () -> {
            productController.updateProductsById(productId, updatedProduct);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productService, times(1)).updateProduct(updatedProduct, productId);
    }

    @Test
    public void getProductByName(){
            String productName = "Test Product";
            Product product = buildProduct();

            when(productService.getProductByName(productName)).thenReturn(product);

            ResponseEntity<Product> response = productController.getProductByName(productName);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(product, response.getBody());
        }

        private Product buildProduct() {
            return Product.builder()
                    .id(1L)
                    .name("Test Product")
                    .description("Test Description")
                    .sku("TEST_SKU")
                    .price(9.99)
                    .created_at(LocalDateTime.now())
                    .modified_at(LocalDateTime.now())
                    .deleted_at(null)
                    .build();
        }

    @Test
    public void getProductByCategory(){
            Long categoryId = 1L;
            List<Product> products = Arrays.asList(buildProducts(), buildProducts());

            when(productService.getProductsByCategory(categoryId)).thenReturn(products);

            ResponseEntity<List<Product>> response = productController.getProductsByCategory(categoryId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(products, response.getBody());

            verify(productService).getProductsByCategory(categoryId);
        }

        private Product buildProducts() {
            return Product.builder()
                    .id(1L)
                    .name("Test Product")
                    .description("Test Description")
                    .sku("TEST_SKU")
                    .price(9.99)
                    .created_at(LocalDateTime.now())
                    .modified_at(LocalDateTime.now())
                    .deleted_at(null)
                    .build();

    }

    @Test
    public void testAddProduct() {
    Product product = Product.builder()
            .id(1L)
            .name("Test Product")
            .description("Test Description")
            .sku("TEST_SKU")
            .price(9.99)
            .created_at(LocalDateTime.now())
            .modified_at(LocalDateTime.now())
            .deleted_at(null)
            .build();

    when(productService.addProducts(any(Product.class))).thenReturn(ResponseEntity.ok(product));

    ResponseEntity<Product> response = productController.addProduct(product);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(product, response.getBody());
    verify(productService).addProducts(product);

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
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}