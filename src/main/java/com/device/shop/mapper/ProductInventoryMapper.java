package com.device.shop.mapper;

import com.device.shop.entity.ProductInventory;

import com.device.shop.model.ProductInventoryDTO;
import org.springframework.stereotype.Component;
//ToDo
@Component
public class ProductInventoryMapper {

    public ProductInventoryDTO toDTO(ProductInventory productInventory) {
        return ProductInventoryDTO.builder()
                .id(productInventory.getId())
                .quantify(productInventory.getQuantify())
                .createdAt(productInventory.getCreatedAt())
                .modifiedAt(productInventory.getModifiedAt())
                .deletedAt(productInventory.getDeletedAt())
                .build();
    }

    public ProductInventory toEntity(ProductInventoryDTO productInventoryDTO) {
        return ProductInventory.builder()
                .id(productInventoryDTO.getId())
                .quantify(productInventoryDTO.getQuantify())
                .createdAt(productInventoryDTO.getCreatedAt())
                .modifiedAt(productInventoryDTO.getModifiedAt())
                .deletedAt(productInventoryDTO.getDeletedAt())
                .build();
    }
}
