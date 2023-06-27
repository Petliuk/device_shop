package com.device.shop.mapper;

import com.device.shop.entity.CartItem;
import com.device.shop.model.CartItemDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItem toEntity(CartItemDTO dto) {
        return CartItem.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .build();

    }

    public CartItemDTO toDTO(CartItem entity) {
        return CartItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .productId(entity.getProduct().getId())
                .shoppingSessionId(entity.getShoppingSession().getId())
                .build();
    }

}