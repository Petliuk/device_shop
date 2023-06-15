package com.device.shop.service;

import com.device.shop.entity.OrderDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.OrderDetailsRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor

public class OrderDetailsService implements OrderDetailsServiceInterface {

    OrderDetailsRepository orderDetailsRepository;

    @Transactional
    public OrderDetails getOrderDetailsById(Long orderId) {
        return orderDetailsRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order Details  with id " + orderId + " not found"));
    }

    @Transactional
    public OrderDetails createOrder(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Transactional
    public OrderDetails updateOrderDetailsById(OrderDetails orderDetails, Long orderDetailsId) throws BadRequestException, EntityNotFoundException {
        if (orderDetailsId == null || !orderDetailsRepository.existsById(orderDetailsId)) {
            throw new EntityNotFoundException("User with id " + orderDetailsId + " not found");
        } else if (!orderDetailsId.equals(orderDetails.getId())) {
            throw new BadRequestException("Cannot change the id to " + orderDetails.getId());
        } else {
            return orderDetailsRepository.save(orderDetails);
        }
    }

    @Transactional
    public void deleteOrderDetailsById(Long orderDetailsId) {
        if (orderDetailsRepository.existsById(orderDetailsId)) {
            orderDetailsRepository.deleteById(orderDetailsId);
        } else {
            throw new EntityNotFoundException("Order Details with id " + orderDetailsId + " not found");
        }

    }

}