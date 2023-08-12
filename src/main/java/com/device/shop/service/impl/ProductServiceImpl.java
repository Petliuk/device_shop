package com.device.shop.service.impl;

import com.device.shop.csv.CSVHelper;
import com.device.shop.entity.Discount;
import com.device.shop.entity.Product;
import com.device.shop.entity.ProductPhoto;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.ProductMapper;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.DiscountRepository;
import com.device.shop.repository.ProductPhotoRepository;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    //toDo decouple
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DiscountRepository discountRepository;
    private final ProductPhotoRepository productPhotoRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
        return productMapper.toDTO(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByProductCategory_Id(categoryId);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) throws BadRequestException, EntityNotFoundException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        if (!productId.equals(productDTO.getId())) {
            throw new BadRequestException("Cannot change the id to " + productDTO.getId());
        }
        Product updatedProduct = productMapper.toEntity(productDTO);

        Discount discount = discountRepository.findById(productDTO.getDiscountId())
                .orElseThrow(() -> new EntityNotFoundException("Discount with id " + productDTO.getDiscountId() + " not found"));
        updatedProduct.setDiscount(discount);

        updatedProduct.setId(existingProduct.getId());

        return productMapper.toDTO(productRepository.save(updatedProduct));
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

    @Transactional
    public ResponseEntity<ProductDTO> addProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);

        if (productDTO.getPhotoId() != null) {
            ProductPhoto productPhoto = productPhotoRepository.findById(productDTO.getPhotoId())
                    .orElseThrow(() -> new EntityNotFoundException("Фото з id " + productDTO.getPhotoId() + " не знайдено"));
            product.setProductPhoto(productPhoto);
        }

        Discount discount = discountRepository.findById(productDTO.getDiscountId())
                .orElseThrow(() -> new EntityNotFoundException("Discount with id " + productDTO.getDiscountId() + " not found"));
        product.setDiscount(discount);

        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDTO(product));
    }

}