package com.device.shop.service;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long productId);

    void deleteProduct(Long productId);

    ResponseEntity<ProductDTO> addProducts(ProductDTO productDTO);

    ProductDTO getProductByName(String name);

    List<ProductDTO> getProductsByCategory(Long categoryId);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId) throws BadRequestException, EntityNotFoundException;

    void save(MultipartFile file) throws IOException, BadRequestException;

}