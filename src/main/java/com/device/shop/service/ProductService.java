package com.device.shop.service;

import com.device.shop.entity.Product;
import com.device.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceInterface{

    ProductRepository productRepository;
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

