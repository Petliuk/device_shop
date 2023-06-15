package com.device.shop.repository;

import com.device.shop.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {

}