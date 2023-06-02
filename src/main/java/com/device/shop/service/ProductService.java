package com.device.shop.service;

import com.device.shop.csv.CSVHelper;
import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceInterface {


    ProductRepository productRepository;

    @Transactional
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Transactional
    public void save(MultipartFile file) throws IOException , BadRequestException {
        if (!CSVHelper.hasCSVFormat(file)) {
            throw new BadRequestException("Please upload a csv file");
        }
        try {
            List<Product> products = CSVHelper.csvToProducts(file.getInputStream());
            productRepository.saveAll(products);
        } catch (IOException e) {
            throw new IOException("Failed to store CSV data: " + e.getMessage());
        }
    }

}
