package com.device.shop.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Long id;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long shoppingSessionId;
    private Long productId;

}