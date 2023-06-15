package com.device.shop.controller;

import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.service.ProductService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    ProductService productService;

    @PostMapping("/update/product/{id}")
    public ResponseEntity<Product> updateProductsById(@PathVariable ("id")Long priductid,
                                                     @RequestBody Product product ) throws BadRequestException{
        Product updateProduct = productService.updateProduct(product, priductid);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/delete/products/{id}")
     public ResponseEntity<String> deleteProductsById (@PathVariable("id") Long  productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);

    }

    @GetMapping ("/products/{id}")
    public ResponseEntity <Product> getProductById (@PathVariable("id") Long productId) {
       Product product = productService.getProductById(productId);
       return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return productService.addProducts(product);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        productService.save(file);
        return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
    }

}


