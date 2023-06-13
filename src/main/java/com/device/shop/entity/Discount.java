package com.device.shop.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    @Id
    Long id;
    String name;
    String description;
    String discount_percent;
    LocalDateTime created_at;
    LocalDateTime modified_at;

    @OneToMany(mappedBy = "discount")
    List<Product> products;

}