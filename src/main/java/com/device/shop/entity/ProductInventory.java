package com.device.shop.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_inventory")
public class ProductInventory {
    @Id
    Long id;
    Long quantify;
    LocalDateTime created_at;
    LocalDateTime modified_at;
    LocalDateTime deleted_at;

    @OneToOne(mappedBy = "productInventory", cascade = CascadeType.ALL)
    Product product;

    public ProductInventory() {
    }

    public ProductInventory(Long id, Long quantify, LocalDateTime created_at, LocalDateTime modified_at, LocalDateTime deleted_at, Product product) {
        this.id = id;
        this.quantify = quantify;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.deleted_at = deleted_at;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantify() {
        return quantify;
    }

    public void setQuantify(Long quantify) {
        this.quantify = quantify;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
