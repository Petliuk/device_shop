package com.device.shop.service.impl;

import com.device.shop.entity.*;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.OrderDetailsMapper;
import com.device.shop.mapper.OrderItemsMapper;
import com.device.shop.model.OrderItemsDTO;
import com.device.shop.repository.OrderItemsRepository;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.OrderDetailsService;
import com.device.shop.service.OrderItemsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class OrderItemsServiceImpl implements OrderItemsService {
    //toDo decouple
    private final OrderItemsRepository orderItemsRepository;
    private final OrderItemsMapper orderItemsMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderDetailsService orderDetailsService;
    private final ProductRepository productRepository;

    @Transactional
    public OrderItemsDTO getOrderItemsById(Long orderItemsId) {
        OrderItems orderItems = orderItemsRepository.findById(orderItemsId)
                .orElseThrow(() -> new EntityNotFoundException("Order Items with id " + orderItemsId + " not found"));
        return orderItemsMapper.toDTO(orderItems);
    }

    @Transactional
    public OrderItemsDTO addOrderItems(Long productId, OrderItemsDTO orderItemsDTO) {
       OrderItems orderItems = orderItemsMapper.toEntity(orderItemsDTO);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
        orderItems.setProduct(product);

        OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsService.getOrderDetailsById(orderItemsDTO.getOrderDetailsId()));
        orderItems.setOrderDetails(orderDetails);

        OrderItems newOrderItems = orderItemsRepository.save(orderItems);

        return orderItemsMapper.toDTO(newOrderItems);
    }

    @Transactional
    public OrderItemsDTO updateOrderItemsById(OrderItemsDTO orderItemsDTO, Long orderItemsId) throws BadRequestException {
        OrderItems orderItems = orderItemsRepository.findById(orderItemsId)
                .orElseThrow(() -> new EntityNotFoundException("Order Items with id " + orderItemsId + " not found"));

        if (!orderItemsDTO.getId().equals(orderItems.getId())) {
            throw new BadRequestException("Cannot change the id to " + orderItemsDTO.getId());
        }

        orderItems.setOrderId(orderItemsDTO.getOrderId());
        orderItems.setCreatedAt(orderItemsDTO.getCreatedAt());
        orderItems.setModifiedAt(orderItemsDTO.getModifiedAt());

        orderItemsRepository.save(orderItems);
        return orderItemsMapper.toDTO(orderItems);
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