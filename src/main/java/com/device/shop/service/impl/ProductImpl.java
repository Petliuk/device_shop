package com.device.shop.service.impl;

import com.device.shop.csv.CSVHelper;
import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ProductImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMap;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
        return convertToDto(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
    }

    @Transactional
    public ResponseEntity<ProductDTO> addProducts(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        productRepository.save(product);
        return ResponseEntity.ok(convertToDto(product));
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductByName(String name) {
        Product product = productRepository.findByName(name);
        return convertToDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByProductCategory_Id(categoryId);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) throws BadRequestException, EntityNotFoundException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        if (!productId.equals(productDTO.getId())) {
            throw new BadRequestException("Cannot change the id to " + productDTO.getId());
        }

        Product updatedProduct = convertToEntity(productDTO);
        updatedProduct.setId(existingProduct.getId());

        return convertToDto(productRepository.save(updatedProduct));
    }

    @Transactional
    public void save(MultipartFile file) throws IOException, BadRequestException {
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

    private ProductDTO convertToDto(Product product) {
        return modelMap.map(product, ProductDTO.class);
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return modelMap.map(productDTO, Product.class);
    }

}