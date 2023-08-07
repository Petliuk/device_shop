package com.device.shop.service.impl;
import com.device.shop.entity.ProductPhoto;
import com.device.shop.repository.ProductPhotoRepository;
import com.device.shop.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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
}
