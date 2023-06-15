package com.device.shop.service;

import com.device.shop.csv.CSVHelper;
import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
    public Product getProductById (Long productId){
       return productRepository.findById(productId)
               .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
    }

    @Transactional
    public void deleteProduct (Long productId){
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
    }

    @Transactional
    public ResponseEntity<Product> addProducts(Product product) {
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @Transactional(readOnly = true)
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByProductCategory_Id(categoryId);
    }

    @Transactional
    public Product updateProduct (Product product, Long productId ) throws BadRequestException, EntityNotFoundException {
        if (productId != null || productRepository.existsById(productId)){
              throw new EntityNotFoundException("Product with id " + productId + " not found");
        } else if (!productId.equals(product.getId())) {
              throw new BadRequestException("Cannot change the id to " + product.getId());
        } else {
               return productRepository.save(product);
         }
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