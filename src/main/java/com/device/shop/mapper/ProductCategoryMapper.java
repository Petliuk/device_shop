package com.device.shop.mapper;

import com.device.shop.entity.ProductCategory;
import com.device.shop.model.ProductCategoryDTO;
import org.springframework.stereotype.Component;
//ToDo
@Component
public class ProductCategoryMapper {

    public ProductCategory toEntity(ProductCategoryDTO dto) {
        return ProductCategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .deletedAt(dto.getDeletedAt())
                .build();
    }

    public ProductCategoryDTO toDTO(ProductCategory entity) {
        return ProductCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

}