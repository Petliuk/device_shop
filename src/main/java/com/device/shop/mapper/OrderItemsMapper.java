package com.device.shop.mapper;

import com.device.shop.entity.OrderItems;
import com.device.shop.model.OrderItemsDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {

    public OrderItems toEntity(OrderItemsDTO dto) {
        return OrderItems.builder()
                .id(dto.getId())
                .orderId(dto.getOrderId())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .build();
    }

    public OrderItemsDTO toDTO(OrderItems entity) {
        return OrderItemsDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .productId(entity.getProduct().getId())
                .orderDetailsId(entity.getOrderDetails().getId())
                .build();
    }

}