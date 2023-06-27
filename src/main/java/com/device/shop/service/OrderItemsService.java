package com.device.shop.service;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.OrderItemsDTO;


public interface OrderItemsService {

    OrderItemsDTO getOrderItemsById(Long orderItemsId);

    OrderItemsDTO addOrderItems(Long productId, OrderItemsDTO orderItemsDTO);

    OrderItemsDTO updateOrderItemsById(OrderItemsDTO orderItemsDTO, Long orderItemsId) throws BadRequestException;

    void deleteOrderItemsById(Long orderItemsId);

}