package com.device.shop.model;

import com.device.shop.entity.PaymentDetails;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsDTO {

    private Long id;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<OrderItemsDTO> orderItems;
    private PaymentDetails paymentDetails;
    private Long userId;

}
