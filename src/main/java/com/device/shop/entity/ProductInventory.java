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
    LocalDateTime created_at;
    LocalDateTime modified_at;
    LocalDateTime deleted_at;

    @OneToOne(mappedBy = "productInventory", cascade = CascadeType.ALL)
    Product product;

}
