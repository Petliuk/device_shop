package com.device.shop.mapper;

import com.device.shop.entity.OrderItems;
import com.device.shop.model.OrderItemsDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {

    public static OrderItems toEntity(OrderItemsDTO dto) {
        return OrderItems.builder()
                .id(dto.getId())
                .orderId(dto.getOrderId())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .product(dto.getProduct())
                .orderDetails(dto.getOrderDetails())
                .build();
    }

    public static OrderItemsDTO toDTO(OrderItems entity) {
        return OrderItemsDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .product(entity.getProduct())
                .orderDetails(entity.getOrderDetails())
                .build();
    }
}
