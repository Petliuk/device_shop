package com.device.shop.test.service;

/*import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")*/
public class ProductServiceTest {

    //toDo Change all tests to work correctly

 /*   private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        //productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testGetAllProducts_ReturnsListOfProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("phone");
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("laptop");
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> retrievedList = productService.getAllProducts();

        assertEquals(2, retrievedList.size());
        assertEquals("phone", retrievedList.get(0).getName());
        assertEquals("laptop", retrievedList.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById_WithValidId_ReturnsProductDTO() {
        Product product = new Product();
        product.setId(1L);
        product.setName("phone");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO retrievedProduct = productService.getProductById(1L);

        assertNotNull(retrievedProduct);
        assertEquals("phone", retrievedProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductById_WithInvalidId_ThrowsEntityNotFoundException() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteProduct_WithExistingId_DeletesProduct() {

        when(productRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProduct_WithNonExistingId_ThrowsEntityNotFoundException() {

        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testUpdateProduct_WithValidData_ReturnsUpdatedProductDTO() throws BadRequestException, EntityNotFoundException {

        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Updated Product");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Existing Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        when(productRepository.save(any(Product.class))).then(AdditionalAnswers.returnsFirstArg());

        ProductDTO updatedProduct = productService.updateProduct(productDTO, productId);

        assertNotNull(updatedProduct);
        assertEquals(productId, updatedProduct.getId());
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct_WithInvalidId_ThrowsBadRequestException() {

        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2L);
        productDTO.setName("Updated Product");
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Existing Product");
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        assertThrows(BadRequestException.class, () -> productService.updateProduct(productDTO, productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct_WithNonExistingId_ThrowsEntityNotFoundException() {

        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Updated Product");
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(productDTO, productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testGetProductByName_ReturnsProductDTO() {

        String productName = "Test Product";
        Product product = new Product();
        product.setId(1L);
        product.setName(productName);
        when(productRepository.findByName(productName)).thenReturn(product);

        ProductDTO retrievedProduct = productService.getProductByName(productName);

        assertNotNull(retrievedProduct);
        assertEquals(productName, retrievedProduct.getName());
        verify(productRepository, times(1)).findByName(productName);
    }

    @Test
    public void testGetProductsByCategory_ReturnsListOfProductDTOs() {
        Long categoryId = 1L;
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findByProductCategory_Id(categoryId)).thenReturn(products);

        List<ProductDTO> retrievedProducts = productService.getProductsByCategory(categoryId);

        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
        assertEquals("Product 1", retrievedProducts.get(0).getName());
        assertEquals("Product 2", retrievedProducts.get(1).getName());
        verify(productRepository, times(1)).findByProductCategory_Id(categoryId);
    }


    @Test
    public void testAddProducts_ReturnsProductDTO() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");

        Product savedProduct = new Product();
        savedProduct.setId(productDTO.getId());
        savedProduct.setName(productDTO.getName());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ResponseEntity<ProductDTO> response = productService.addProducts(productDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    void save_InvalidCSVFile_ThrowsBadRequestException() {
        ProductRepository productRepository = mock(ProductRepository.class);
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "invalid csv".getBytes());

        assertThrows(BadRequestException.class, () -> productService.save(file));
        verifyNoInteractions(productRepository);
    }*/

}