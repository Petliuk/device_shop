package com.device.shop.mapper;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.model.ShoppingSessionDTO;
import org.springframework.stereotype.Component;

@Component
public class ShoppingSessionMapper {
    public ShoppingSessionDTO toDTO(ShoppingSession shoppingSession) {
        return ShoppingSessionDTO.builder()
                .id(shoppingSession.getId())
                .total(shoppingSession.getTotal())
                .createdAt(shoppingSession.getCreatedAt())
                .modifiedAt(shoppingSession.getModifiedAt())
                .userId(shoppingSession.getUser().getId())
                .build();
    }

    public ShoppingSession toEntity(ShoppingSessionDTO shoppingSessionDTO) {
        return ShoppingSession.builder()
                .id(shoppingSessionDTO.getId())
                .total(shoppingSessionDTO.getTotal())
                .createdAt(shoppingSessionDTO.getCreatedAt())
                .modifiedAt(shoppingSessionDTO.getModifiedAt())
                .build();
    }

}