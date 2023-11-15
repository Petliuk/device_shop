package com.device.shop.service.impl;

import com.device.shop.csv.CSVHelper;
import com.device.shop.entity.Discount;
import com.device.shop.entity.Product;
import com.device.shop.entity.ProductCategory;
import com.device.shop.entity.ProductPhoto;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.ProductMapper;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.DiscountRepository;
import com.device.shop.repository.ProductCategoryRepository;
import com.device.shop.repository.ProductPhotoRepository;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ProductCategoryRepository productCategoryRepository;


/*
  @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

*/


    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
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



 /*   @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByProductCategory_Id(categoryId);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }*/

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByProductCategory_Id(categoryId);
        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }


    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) throws BadRequestException, EntityNotFoundException {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        if (!productId.equals(productDTO.getId())) {
            throw new BadRequestException("Cannot change the id to " + productDTO.getId());
        }
        Product updatedProduct = productMapper.toEntity(productDTO);

        //========================================================================
        Discount discount = discountRepository.findById(productDTO.getDiscountId()).orElseThrow(() -> new EntityNotFoundException("Discount with id " + productDTO.getDiscountId() + " not found"));
        updatedProduct.setDiscount(discount);

        //========================================================================

        ProductCategory productCategory = productCategoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("ProductCategory with id " + productDTO.getCategoryId() + " not found"));
        updatedProduct.setProductCategory(productCategory);

        //========================================================================

        ProductPhoto productPhoto = productPhotoRepository.findById(productDTO.getPhotoId()).orElseThrow(() -> new EntityNotFoundException("ProductPhoto with id " + productDTO.getPhotoId() + " not found"));
        updatedProduct.setProductPhoto(productPhoto);


        //========================================================================
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

        Long photoId = productDTO.getPhotoId(); // Отримуємо photoId з productDTO

        if (photoId != null) {
            ProductPhoto productPhoto = productPhotoRepository.findById(photoId).orElseThrow(() -> new EntityNotFoundException("ProductPhoto with id " + photoId + " not found"));
            product.setProductPhoto(productPhoto);
        } else {
            product.setProductPhoto(null); // Встановлюємо значення null, якщо photoId == null
        }

        if (productDTO.getDiscountId() != null) {
            Discount discount = discountRepository.findById(productDTO.getDiscountId()).orElseThrow(() -> new EntityNotFoundException("Discount with id " + productDTO.getDiscountId() + " not found"));
            product.setDiscount(discount);
        } else {
            product.setDiscount(null);
        }

        // Отримайте categoryId з productDTO
        Long categoryId = productDTO.getCategoryId();

        if (categoryId != null) {
            ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("ProductCategory with id " + categoryId + " not found"));
            product.setProductCategory(productCategory);
        } else {
            product.setProductCategory(null);
        }

        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDTO(product));
    }
}