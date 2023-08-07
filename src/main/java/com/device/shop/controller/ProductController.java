package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.ProductDTO;
import com.device.shop.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProductsById(@PathVariable("id") Long productId, @RequestBody ProductDTO productDTO) throws BadRequestException {
        ProductDTO updateProductDAO = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updateProductDAO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> productDTO = productService.getAllProducts();
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long productId) {
        ProductDTO productDTO = productService.getProductById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@PathVariable String name) {
        List<ProductDTO> productDTOs = productService.searchProductsByName(name);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/categories/{categoryId}")
    //toDo add logic later + make dynamic search
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<ProductDTO> productsDTO = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        productService.save(file);
        return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) throws IOException {
        ProductDTO addedProductDTO = productService.addProduct(productDTO).getBody();
        return ResponseEntity.ok(addedProductDTO);
    }

}