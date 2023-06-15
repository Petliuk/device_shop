package com.device.shop.repository;

import com.device.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository  extends JpaRepository<CartItem,Long> {

    List<CartItem> findAllByShoppingSessionId(Long cartId);

}