package com.device.shop.service;

import com.device.shop.entity.OrderItems;
import com.device.shop.exception.BadRequestException;

import javax.persistence.EntityNotFoundException;

public interface OrderItemsServiceInterface {

    OrderItems getOrderItemsById(Long orderItemsId);
    OrderItems addOrderItems(Long productId);
    OrderItems updateOrderItemsById (OrderItems orderItems, Long orderItemsId) throws BadRequestException, EntityNotFoundException;
    void deleteOrderItemsById(Long orderItemsId);

}