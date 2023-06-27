package com.device.shop.service;

import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;

import java.util.List;

public interface CartItemService {

    CartItemDTO addToCart(Long id, CartItemDTO cartItemDTO);

    List<ProductDTO> getProductsInCart(Long cartId);

    void deleteTheProduct(Long cartId);

    void removeAllProductsFromCart(Long cartId);

}