package com.device.shop.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPhoto {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo_data")
    private byte[] photoData;

    @OneToOne(mappedBy = "productPhoto")
    private Product product;
}
