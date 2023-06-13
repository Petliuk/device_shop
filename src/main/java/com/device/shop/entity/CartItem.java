package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    Long id;
    Long quantity;
    LocalDateTime created_at;
    LocalDateTime modified_at;

    @ManyToOne
    @JoinColumn(name = "session_id")
    ShoppingSession shoppingSession;

    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;

}