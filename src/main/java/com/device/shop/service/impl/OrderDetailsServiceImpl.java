package com.device.shop.service.impl;

import com.device.shop.entity.OrderDetails;
import com.device.shop.entity.PaymentDetails;
import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.OrderDetailsMapper;
import com.device.shop.mapper.PaymentDetailsMapper;
import com.device.shop.model.OrderDetailsDTO;
import com.device.shop.repository.OrderDetailsRepository;
import com.device.shop.repository.UserRepository;
import com.device.shop.service.OrderDetailsService;
import com.device.shop.service.PaymentDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    //toDo decouple
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderDetailsMapper orderDetailsMapper;
    private final UserRepository userRepository;
    private  final PaymentDetailsMapper paymentDetailsMapper;
    private final PaymentDetailsService paymentDetailsService;


    @Transactional
    public OrderDetailsDTO getOrderDetailsById(Long orderId) {
        OrderDetails orderDetails = orderDetailsRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order Details with id " + orderId + " not found"));

        return orderDetailsMapper.toDTO(orderDetails);
    }

    @Transactional
    public OrderDetailsDTO createOrder(OrderDetailsDTO orderDetailsDTO) {
        OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsDTO);

        User user = userRepository.findById(orderDetailsDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + orderDetailsDTO.getUserId() + " not found"));
        orderDetails.setUser(user);

        PaymentDetails paymentDetails = paymentDetailsMapper.toEntity(paymentDetailsService.getPaymentById(orderDetailsDTO.getPaymentDetailsId()));
        orderDetails.setPaymentDetails(paymentDetails);

        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);
        return orderDetailsMapper.toDTO(savedOrderDetails);
    }

    @Transactional
    public OrderDetailsDTO updateOrderDetailsById(OrderDetailsDTO orderDetailsDTO, Long orderDetailsId) throws BadRequestException {
        if (orderDetailsId == null || !orderDetailsRepository.existsById(orderDetailsId)) {
            throw new EntityNotFoundException("Order Details with id " + orderDetailsId + " not found");
        } else if (!orderDetailsId.equals(orderDetailsDTO.getId())) {
            throw new BadRequestException("Cannot change the id to " + orderDetailsDTO.getId());
        } else {
            OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsDTO);
            User user = userRepository.findById(orderDetailsDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User with id " + orderDetailsDTO.getUserId() + " not found"));
            orderDetails.setUser(user);
            OrderDetails updatedOrderDetails = orderDetailsRepository.save(orderDetails);
            return orderDetailsMapper.toDTO(updatedOrderDetails);
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