package com.device.shop.mapper;

import com.device.shop.entity.Discount;
import com.device.shop.model.DiscountDTO;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {

    public Discount toEntity(DiscountDTO dto) {
        return Discount.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .discountPercent(dto.getDiscountPercent())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .build();
    }

    public DiscountDTO toDTO(Discount entity) {
        return DiscountDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .discountPercent(entity.getDiscountPercent())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

}