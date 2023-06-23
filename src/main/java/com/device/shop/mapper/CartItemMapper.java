package com.device.shop.mapper;

import com.device.shop.entity.CartItem;
import com.device.shop.model.CartItemDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public static CartItem toEntity(CartItemDTO dto) {
        return CartItem.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .build();

    }

    public static CartItemDTO toDTO(CartItem entity) {
        return CartItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .build();
    }

}