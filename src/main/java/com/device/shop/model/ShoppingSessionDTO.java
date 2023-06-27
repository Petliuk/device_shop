package com.device.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingSessionDTO {
    private Long id;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;

}