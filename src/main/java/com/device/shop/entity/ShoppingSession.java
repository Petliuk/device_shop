package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shopping_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingSession {

    @Id
    Long id;
    Double total;
    LocalDateTime created_at;
    LocalDateTime modified_at;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "shoppingSession")
    List<CartItem> cartItems;

}