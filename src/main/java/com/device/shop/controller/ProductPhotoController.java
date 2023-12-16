package com.device.shop.controller;

import com.device.shop.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/photos")
public class ProductPhotoController {

    private final ProductPhotoService productPhotoService;

    @Autowired
    public ProductPhotoController(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> uploadPhoto(@RequestPart("image") MultipartFile image) throws IOException {
        Long photoId = productPhotoService.uploadPhoto(image);
        return ResponseEntity.ok(photoId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{photoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> updatePhoto(@PathVariable Long photoId, @RequestPart("image") MultipartFile image) throws IOException {
        Long updatedPhotoId = productPhotoService.updatePhoto(photoId, image);
        return ResponseEntity.ok(updatedPhotoId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long photoId) {
        byte[] photoData = productPhotoService.getPhoto(photoId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photoData);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Iterable<Long>> getAllPhotoIds() {
        Iterable<Long> photoIds = productPhotoService.getAllPhotoIds();
        return ResponseEntity.ok(photoIds);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) {
        productPhotoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }



}