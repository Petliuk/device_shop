package com.device.shop.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private Long id;
    private String name;
    private String description;
    private String discountPercent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}