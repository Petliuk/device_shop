package com.device.shop.repository;

import com.device.shop.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Long> {

}