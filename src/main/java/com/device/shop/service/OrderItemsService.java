package com.device.shop.service;

import com.device.shop.entity.OrderItems;
import com.device.shop.entity.Product;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.OrderItemsRepository;
import com.device.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class OrderItemsService implements OrderItemsServiceInterface {

    OrderItemsRepository orderItemsRepository;
    ProductRepository productRepository;

    @Transactional
    public OrderItems getOrderItemsById(Long orderItemsId) {
        return orderItemsRepository.findById(orderItemsId)
                .orElseThrow(() -> new EntityNotFoundException("Order Items with id " + orderItemsId + " not found"));
    }

    @Transactional
    public OrderItems addOrderItems(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        OrderItems orderItems = new OrderItems();
        orderItems.setProduct(product);
        orderItemsRepository.save(orderItems);

        return orderItems;
    }

    @Transactional
    public OrderItems updateOrderItemsById (OrderItems orderItems, Long orderItemsId) throws BadRequestException, EntityNotFoundException {
        if (orderItemsId == null || !orderItemsRepository.existsById(orderItemsId)) {
            throw new EntityNotFoundException("User with id " + orderItemsId + " not found");
        } else if (!orderItemsId.equals(orderItems.getId())) {
            throw new BadRequestException("Cannot change the id to " + orderItems.getId());
        } else {
            return orderItemsRepository.save(orderItems);
        }
    }

    @Transactional
    public void deleteOrderItemsById(Long orderItemsId) {
        if (orderItemsRepository.existsById(orderItemsId)) {
            orderItemsRepository.deleteById(orderItemsId);
        } else {
            throw new EntityNotFoundException("Order Items with id " + orderItemsId + " not found");
        }

    }

}