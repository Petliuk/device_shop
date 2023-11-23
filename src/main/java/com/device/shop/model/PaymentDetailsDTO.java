package com.device.shop.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailsDTO {

    private Long id;
    private Long orderId;
    private Long amount;
    private String provider;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}