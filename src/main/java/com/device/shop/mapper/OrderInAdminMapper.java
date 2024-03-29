package com.device.shop.mapper;

import com.device.shop.entity.OrderInAdmin;
import com.device.shop.model.OrderDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderInAdminMapper {

    public OrderInAdmin toEntity(OrderDTO orderDTO) {
        return OrderInAdmin.builder()
                .userId(orderDTO.getUserId())
                .productId(orderDTO.getProductId())
                .address(orderDTO.getAddress())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public OrderDTO toDTO(OrderInAdmin orderInAdmin) {
        return OrderDTO.builder()
                .id(orderInAdmin.getId())
                .userId(orderInAdmin.getUserId())
                .productId(orderInAdmin.getProductId())
                .address(orderInAdmin.getAddress())
                .build();
    }


}