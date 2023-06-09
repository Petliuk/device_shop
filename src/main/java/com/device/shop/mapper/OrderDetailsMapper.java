package com.device.shop.mapper;

import com.device.shop.entity.OrderDetails;
import com.device.shop.model.OrderDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsMapper {
    public OrderDetails toEntity(OrderDetailsDTO dto) {
        return OrderDetails.builder()
                .id(dto.getId())
                .total(dto.getTotal())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .build();
    }

    public OrderDetailsDTO toDTO(OrderDetails entity) {
        return OrderDetailsDTO.builder()
                .id(entity.getId())
                .total(entity.getTotal())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .paymentDetailsId(entity.getPaymentDetails().getId())
                .userId(entity.getUser().getId())
                .build();
    }

}