package com.device.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductPhotoService {
    Long uploadPhoto(MultipartFile image) throws IOException;
}
