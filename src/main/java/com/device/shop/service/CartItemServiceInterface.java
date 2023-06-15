package com.device.shop.service;

import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;

import java.util.List;

public interface CartItemServiceInterface {

    CartItem addToCart(Long productId);
    List<Product> getProductsInCart(Long productId);
    void deleteTheProduct (Long cartId);
    void removeAllProductsFromCart(Long cartId);

}
