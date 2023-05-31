package com.device.shop.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    Long id;
    String name;
    String description;
    LocalDateTime created_at;
    LocalDateTime modified_at;
    LocalDateTime deleted_at;

    @OneToMany(mappedBy = "productCategory")
    List<Product> products;

}