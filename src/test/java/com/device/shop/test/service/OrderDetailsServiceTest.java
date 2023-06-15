package com.device.shop.test.service;

import com.device.shop.entity.OrderDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.OrderDetailsRepository;
import com.device.shop.service.OrderDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderDetailsServiceTest {

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderDetailsService orderDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrderDetails() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(1L);
        orderDetails.setId(100L);

        when(orderDetailsRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);

        OrderDetails savedOrderDetails = orderDetailsService.createOrder(orderDetails);

        Assertions.assertEquals(orderDetails.getId(), savedOrderDetails.getId());
        Assertions.assertEquals(orderDetails.getId(), savedOrderDetails.getId());

        verify(orderDetailsRepository).save(any(OrderDetails.class));
    }

    @Test
    public void testGetOrderDetailsById() {
        Long orderDetailsId = 1L;
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(orderDetailsId);

        when(orderDetailsRepository.findById(orderDetailsId)).thenReturn(java.util.Optional.of(orderDetails));

        OrderDetails retrievedOrderDetails = orderDetailsService.getOrderDetailsById(orderDetailsId);

        Assertions.assertEquals(orderDetailsId, retrievedOrderDetails.getId());

        verify(orderDetailsRepository).findById(orderDetailsId);
    }

    @Test
    public void testGetOrderDetailsById_NotFound() {
        Long orderDetailsId = 1L;

        when(orderDetailsRepository.findById(orderDetailsId)).thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            orderDetailsService.getOrderDetailsById(orderDetailsId);
        });

        verify(orderDetailsRepository).findById(orderDetailsId);
    }

    @Test
    public void testUpdateOrderDetailsById_OrderDetailsExistsAndIdMatches() throws BadRequestException, EntityNotFoundException {
        Long orderDetailsId = 1L;
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(orderDetailsId);

        when(orderDetailsRepository.existsById(orderDetailsId)).thenReturn(true);
        when(orderDetailsRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);

        OrderDetails result = orderDetailsService.updateOrderDetailsById(orderDetails, orderDetailsId);

        Assertions.assertEquals(orderDetails, result);

        verify(orderDetailsRepository).existsById(orderDetailsId);
        verify(orderDetailsRepository).save(orderDetails);
    }
    @Test
    public void testDeleteOrderDetailsById_OrderDetailsExists() {
        Long orderDetailsId = 1L;

        when(orderDetailsRepository.existsById(orderDetailsId)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> {
            orderDetailsService.deleteOrderDetailsById(orderDetailsId);
        });

        verify(orderDetailsRepository).existsById(orderDetailsId);
        verify(orderDetailsRepository).deleteById(orderDetailsId);
    }

    @Test
    public void testDeleteOrderDetailsById_OrderDetailsNotFound() {
        Long orderDetailsId = 1L;

        when(orderDetailsRepository.existsById(orderDetailsId)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            orderDetailsService.deleteOrderDetailsById(orderDetailsId);
        });

        verify(orderDetailsRepository).existsById(orderDetailsId);
        verify(orderDetailsRepository, never()).deleteById(orderDetailsId);
    }

}