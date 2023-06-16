package com.device.shop.test.controller;

import com.device.shop.controller.OrderDetailsController;
import com.device.shop.entity.OrderDetails;
import com.device.shop.service.OrderDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderDetailsService orderDetailsService;

    @InjectMocks
    private OrderDetailsController orderDetailsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderDetailsController).build();
    }

    @Test
    public void testGetDetailsAboutTheOrderDetailsById() throws Exception {
        Long orderId = 1L;
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(orderId);

        when(orderDetailsService.getOrderDetailsById(orderId)).thenReturn(orderDetails);

        mockMvc.perform(get("/order/{id}/details", orderId)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(orderId));

        verify(orderDetailsService).getOrderDetailsById(orderId);
    }

    @Test
    public void testCreateOrderDetails() throws Exception {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(1L);

        when(orderDetailsService.createOrder(any(OrderDetails.class))).thenReturn(orderDetails);

        mockMvc.perform(post("/order/{id}/details", 1L).contentType(MediaType.APPLICATION_JSON).content("{\"id\":1}")).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1));

        verify(orderDetailsService).createOrder(any(OrderDetails.class));
    }

    @Test
    public void testUpdateOrderDetailsById() throws Exception {
        Long orderDetailsId = 1L;
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(orderDetailsId);

        when(orderDetailsService.updateOrderDetailsById(any(OrderDetails.class), any(Long.class))).thenReturn(orderDetails);

        mockMvc.perform(put("/order/{id}/details", orderDetailsId).contentType(MediaType.APPLICATION_JSON).content("{\"id\":1}")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(orderDetailsId));

        verify(orderDetailsService).updateOrderDetailsById(any(OrderDetails.class), any(Long.class));
    }

    @Test
    public void testDeleteOrderDetailsById() throws Exception {
        Long orderDetailsId = 1L;

        mockMvc.perform(delete("/order/{id}/details", orderDetailsId)).andExpect(status().isOk()).andExpect(content().string("Order Details successfully deleted!"));

        verify(orderDetailsService).deleteOrderDetailsById(orderDetailsId);
    }

}