package com.device.shop.controller;
import com.device.shop.service.ProductPhotoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController

public class ProductPhotoController {

    private final ProductPhotoService productPhotoService;

    @Autowired
    public ProductPhotoController(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Long> uploadPhoto(@RequestParam("image") MultipartFile image) throws IOException {
        Long photoId = productPhotoService.uploadPhoto(image);
        return ResponseEntity.ok(photoId);
    }
}
