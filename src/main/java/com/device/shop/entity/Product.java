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
    Long id;
    String name;
    String description;
    String sku;
    Double price;
    LocalDateTime created_at;
    LocalDateTime modified_at;
    LocalDateTime deleted_at;


    @OneToOne
    @JoinColumn(name = "product_inventory_id")
    ProductInventory productInventory;
    @ManyToOne
    ProductCategory productCategory;


}
