package com.device.shop.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemsDTO {
    private Long id;
    private Long orderId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long productId;
    private Long orderDetailsId;


}