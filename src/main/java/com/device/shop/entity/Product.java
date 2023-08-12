package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "modified_at")
    LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "inventory_id")
    //toDo add support later
    ProductInventory productInventory;

    @ManyToOne
    //toDo add support later
    ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    List<CartItem> cartItem;

    @ManyToOne
    Discount discount;

    @OneToOne
    @JoinColumn(name = "photo_id")
    ProductPhoto productPhoto;

}