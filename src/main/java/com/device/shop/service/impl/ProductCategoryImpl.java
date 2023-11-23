package com.device.shop.service.impl;

import com.device.shop.entity.ProductCategory;
import com.device.shop.mapper.ProductCategoryMapper;
import com.device.shop.model.ProductCategoryDTO;
import com.device.shop.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryImpl {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper categoryMapper;

    public ProductCategoryImpl(ProductCategoryRepository productCategoryRepository, ProductCategoryMapper categoryMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ProductCategoryDTO addProductCategory(ProductCategoryDTO categoryDTO) {
        ProductCategory category = categoryMapper.toEntity(categoryDTO);
        ProductCategory savedCategory = productCategoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    public List<ProductCategoryDTO> getAllProductCategories() {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductCategoryDTO getProductCategoryById(Long categoryId) {
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("ProductCategory with id " + categoryId + " not found"));
        return categoryMapper.toDTO(category);
    }

    public void deleteProductCategory(Long categoryId) {
        productCategoryRepository.deleteById(categoryId);
    }
}
