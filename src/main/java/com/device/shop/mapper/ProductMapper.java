package com.device.shop.mapper;

import com.device.shop.entity.Discount;
import com.device.shop.entity.Product;

import com.device.shop.model.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        ProductDTO.ProductDTOBuilder builder = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sku(product.getSku())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .deletedAt(product.getDeletedAt())
                .discountId(Optional.ofNullable(product.getDiscount())
                        .map(Discount::getId)
                        .orElse(null));

        if (product.getProductPhoto() != null) {
            builder
                    .photoId(product.getProductPhoto().getId())
                    .imageData(product.getProductPhoto().getPhotoData());
        }

        return builder.build();
    }

    public Product toEntity(ProductDTO productDTO) {
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