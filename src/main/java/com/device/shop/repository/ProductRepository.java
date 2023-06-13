package com.device.shop.repository;

import com.device.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
    List<Product> findByProductCategory_Id(Long categoryId);
}
