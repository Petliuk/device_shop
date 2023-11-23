package com.device.shop.repository;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.model.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Long> {

    Optional<Object> findById(ProductDTO shoppingId);

}