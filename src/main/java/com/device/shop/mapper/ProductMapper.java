package com.device.shop.mapper;

import com.device.shop.entity.Product;

import com.device.shop.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sku(product.getSku())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .deletedAt(product.getDeletedAt())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .createdAt(productDTO.getCreatedAt())
                .modifiedAt(productDTO.getModifiedAt())
                .deletedAt(productDTO.getDeletedAt())
                .build();
    }

}
