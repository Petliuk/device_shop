package com.device.shop.mapper;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.model.PaymentDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsMapper {

    public static PaymentDetails toEntity(PaymentDetailsDTO dto) {
        return PaymentDetails.builder()
                .id(dto.getId())
                .orderId(dto.getOrderId())
                .amount(dto.getAmount())
                .provider(dto.getProvider())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .build();
    }

    public static PaymentDetailsDTO toDTO(PaymentDetails entity) {
        return PaymentDetailsDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .amount(entity.getAmount())
                .provider(entity.getProvider())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

}
