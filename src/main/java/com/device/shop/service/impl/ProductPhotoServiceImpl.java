package com.device.shop.service.impl;

import com.device.shop.entity.ProductPhoto;
import com.device.shop.repository.ProductPhotoRepository;
import com.device.shop.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private final ProductPhotoRepository productPhotoRepository;

    @Autowired
    public ProductPhotoServiceImpl(ProductPhotoRepository productPhotoRepository) {
        this.productPhotoRepository = productPhotoRepository;
    }

    @Override
    public Long uploadPhoto(MultipartFile image) throws IOException {
        ProductPhoto productPhoto = ProductPhoto.builder()
                .photoData(image.getBytes())
                .build();
        productPhotoRepository.save(productPhoto);
        return productPhoto.getId();
    }

    @Override
    public byte[] getPhoto(Long photoId) {
        return productPhotoRepository.findById(photoId)
                .map(ProductPhoto::getPhotoData)
                .orElse(null);
    }

    @Override
    public Iterable<Long> getAllPhotoIds() {
        return StreamSupport.stream(productPhotoRepository.findAll().spliterator(), false)
                .map(ProductPhoto::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePhoto(Long photoId) {
        productPhotoRepository.deleteById(photoId);
    }

    @Override
    public Long updatePhoto(Long photoId, MultipartFile image) throws IOException {
        ProductPhoto existingPhoto = productPhotoRepository.findById(photoId)
                .orElseThrow(() -> new EntityNotFoundException("ProductPhoto with id " + photoId + " not found"));

        existingPhoto.setPhotoData(image.getBytes());
        productPhotoRepository.save(existingPhoto);

        return existingPhoto.getId();
    }
}