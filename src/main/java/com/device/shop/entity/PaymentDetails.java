package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {
    @Id
    Long id;
    Long order_id;
    Long amount;
    String provider;
    String status;
    LocalDateTime created_at;
    LocalDateTime modified_at;

    @OneToOne(mappedBy = "paymentDetails")
     OrderDetails orderDetails;

}
