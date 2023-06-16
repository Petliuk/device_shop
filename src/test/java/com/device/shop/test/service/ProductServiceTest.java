package com.device.shop.test.service;

import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    private Product buildProduct() {
        return Product.builder().id(1L).name("Test Product").description("Test Description").sku("TEST_SKU").price(9.99).created_at(LocalDateTime.now()).modified_at(LocalDateTime.now()).deleted_at(null).build();
    }

    @Test
    public void testGetAllProducts_ReturnsListOfUsers() {
        Product product1 = Product.builder().name("phone").description("*").sku("one").build();
        Product product2 = Product.builder().name("laptop").description("*").sku("one").build();
        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> retrievedList = productService.getAllProducts();
        assertEquals(productList, retrievedList);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductById() {
        Long productId = 1L;
        Product product = buildProduct();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product, result);

        verify(productRepository).findById(productId);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    public void testDeleteProduct_EntityNotFoundException() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository).existsById(productId);
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    public void updateProductById() {
        Long productId = 1L;
        Product expectedProduct = buildProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product actualProduct = productService.getProductById(productId);

        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findById(productId);
    }

    @Test
    public void testGetProductById_EntityNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository).findById(productId);
    }

    @Test
    public void testGetProductByName() {
        String productName = "Test Product";
        Product expectedProduct = buildProduct();
        when(productRepository.findByName(productName)).thenReturn(expectedProduct);

        Product actualProduct = productService.getProductByName(productName);

        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findByName(productName);
    }

    @Test
    public void testGetProductsByCategory() {
        Long categoryId = 1L;
        List<Product> expectedProducts = Arrays.asList(buildProduct(), buildProduct());
        when(productRepository.findByProductCategory_Id(categoryId)).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getProductsByCategory(categoryId);

        assertEquals(expectedProducts.size(), actualProducts.size());
    }

    @Test
    public void testAddProduct() {
        MockitoAnnotations.initMocks(this);
        ProductService productService = new ProductService(productRepository);
        Product product = buildProducts();
        when(productRepository.save(product)).thenReturn(product);

        ResponseEntity<Product> response = productService.addProducts(product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productRepository, times(1)).save(product);
    }

    private Product buildProducts() {
        return Product.builder().id(1L).name("Test Product").description("Test Description").sku("TEST_SKU").price(9.99).created_at(LocalDateTime.now()).modified_at(LocalDateTime.now()).deleted_at(null).build();
    }

    @Test
    void save_InvalidCSVFile_ThrowsBadRequestException() {
        ProductRepository productRepository = mock(ProductRepository.class);
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "invalid csv" .getBytes());

        assertThrows(BadRequestException.class, () -> productService.save(file));
        verifyNoInteractions(productRepository);
    }

}