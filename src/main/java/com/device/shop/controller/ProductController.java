package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.ProductDTO;
import com.device.shop.service.ProductService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProductsById(@PathVariable("id") Long productId, @RequestBody ProductDTO productDTO) throws BadRequestException {
        ProductDTO updateProductDAO = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updateProductDAO, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> productDTO = productService.getAllProducts();
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long productId) {
        ProductDTO productDTO = productService.getProductById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO addedProductDTO = productService.addProducts(productDTO).getBody();
        return ResponseEntity.ok(addedProductDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        ProductDTO productDTO = productService.getProductByName(name);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    //toDo add logic later + make dynamic search
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<ProductDTO> productsDTO = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        productService.save(file);
        return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
    }

}