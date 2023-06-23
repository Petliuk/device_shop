package com.device.shop.model;

import com.device.shop.entity.OrderDetails;
import com.device.shop.entity.Product;
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
    private Product product;
    private OrderDetails orderDetails;


}