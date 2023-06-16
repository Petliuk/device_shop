package com.device.shop.service;

import com.device.shop.entity.OrderDetails;
import com.device.shop.exception.BadRequestException;

import javax.persistence.EntityNotFoundException;

public interface OrderDetailsServiceInterface {

    OrderDetails getOrderDetailsById(Long orderId);

    OrderDetails createOrder(OrderDetails orderDetails);

    OrderDetails updateOrderDetailsById(OrderDetails orderDetails, Long orderDetailsId) throws BadRequestException, EntityNotFoundException;

    void deleteOrderDetailsById(Long orderDetailsId);

}