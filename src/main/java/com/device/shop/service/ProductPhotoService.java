package com.device.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductPhotoService {
    Long uploadPhoto(MultipartFile image) throws IOException;
    byte[] getPhoto(Long photoId);
    Iterable<Long> getAllPhotoIds();
    void deletePhoto(Long photoId);
    Long updatePhoto(Long photoId, MultipartFile image) throws IOException;
}