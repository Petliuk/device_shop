package com.device.shop.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long userId;
    private Long productId;
    private String address;
    private UserDTO user;
    private ProductDTO product;


    public void setUser(UserDTO userDTO) {
    }

    public void setProduct(ProductDTO productDTO) {
    }
}
