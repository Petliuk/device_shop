package com.device.shop.controller;

import com.device.shop.model.ProductCategoryDTO;
import com.device.shop.service.impl.ProductCategoryImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-categories")
@AllArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryImpl productCategoryService;

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductCategoryDTO> addProductCategory(@RequestBody ProductCategoryDTO categoryDTO) {
        ProductCategoryDTO addedCategoryDTO = productCategoryService.addProductCategory(categoryDTO);
        return ResponseEntity.ok(addedCategoryDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories() {
        List<ProductCategoryDTO> categories = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok(categories);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategoryById(@PathVariable("id") Long categoryId) {
        ProductCategoryDTO categoryDTO = productCategoryService.getProductCategoryById(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable("id") Long categoryId) {
        productCategoryService.deleteProductCategory(categoryId);
        return ResponseEntity.ok("Product Category successfully deleted!");
    }
}
