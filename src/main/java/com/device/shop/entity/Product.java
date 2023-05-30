package com.device.shop.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    Long id;
    String name;
    String description;
    String SKU;
    Double price;
    LocalDateTime created_at;
    LocalDateTime modified_at;
    LocalDateTime deleted_at;


    @OneToOne
    @JoinColumn(name = "product_inventory_id")
    ProductInventory productInventory;
    @ManyToOne
    ProductCategory productCategory;

    public Product() {
    }

    public Product(Long id, String name, String description, String SKU, Double price, LocalDateTime created_at, LocalDateTime modified_at, LocalDateTime deleted_at, ProductInventory productInventory, ProductCategory productCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.SKU = SKU;
        this.price = price;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.deleted_at = deleted_at;
        this.productInventory = productInventory;
        this.productCategory = productCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public ProductInventory getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
