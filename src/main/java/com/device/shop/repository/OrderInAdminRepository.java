package com.device.shop.repository;

import com.device.shop.entity.OrderInAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInAdminRepository extends JpaRepository<OrderInAdmin, Long> {
    List<OrderInAdmin> findAll();
}