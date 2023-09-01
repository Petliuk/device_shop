package com.device.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventory {

    @Id
    Long id;
    Long quantify;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;
    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @OneToOne(mappedBy = "productInventory", cascade = CascadeType.ALL)
    Product product;

}