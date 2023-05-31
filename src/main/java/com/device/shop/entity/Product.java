package com.device.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product {
    @Id
    Long id;
    String name;
    String description;
    String SKU;
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
