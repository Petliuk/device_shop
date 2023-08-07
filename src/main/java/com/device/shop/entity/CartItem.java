package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
/*@Table(name = "cart_item", uniqueConstraints = @UniqueConstraint(columnNames = "product_id"))*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
  /*  @GeneratedValue*/
    Long id;
    /*@Column(nullable = false)*/
    Long quantity;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "session_id")
    ShoppingSession shoppingSession;

    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;

}