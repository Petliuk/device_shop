package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItems {

    @Id
    Long id;
    Long order_id;
    LocalDateTime created_at;
    LocalDateTime modified_at;

    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_details_id")
    OrderDetails orderDetails;

}