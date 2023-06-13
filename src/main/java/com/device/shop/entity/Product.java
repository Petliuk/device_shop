package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String description;
    String sku;
    Double price;
    LocalDateTime created_at;
    LocalDateTime modified_at;
    LocalDateTime deleted_at;


    @OneToOne
    @JoinColumn(name = "inventory_id")
    ProductInventory productInventory;

    @ManyToOne
    ProductCategory productCategory;

    @OneToOne(mappedBy = "product")
    CartItem cartItem;

    @ManyToOne
    Discount discount;

}
