package com.device.shop.repository;

import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;
import com.device.shop.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByShoppingSessionId(Long cartId);

    CartItem findByProductAndShoppingSession(Product product, ShoppingSession shoppingSession);
/*    List<CartItem> findAllByOrderInAdminId(Long orderId);*/

}