package com.device.shop.service;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.OrderDetailsDTO;


public interface OrderDetailsService {

    OrderDetailsDTO getOrderDetailsById(Long orderId);

    OrderDetailsDTO createOrder(OrderDetailsDTO orderDetailsDTO);

    OrderDetailsDTO updateOrderDetailsById(OrderDetailsDTO orderDetailsDTO, Long orderDetailsId) throws BadRequestException;

    void deleteOrderDetailsById(Long orderDetailsId);

}