package com.device.shop.test.controller;

import com.device.shop.controller.OrderItemsController;
import com.device.shop.entity.OrderItems;
import com.device.shop.service.OrderItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderItemsControllerTest {

       private MockMvc mockMvc;

       @Mock
       private OrderItemsService orderItemsService;

       @InjectMocks
       private OrderItemsController orderItemsController;

       @BeforeEach
       public void setup() {
              MockitoAnnotations.initMocks(this);
              mockMvc = MockMvcBuilders.standaloneSetup(orderItemsController).build();
       }

       @Test
       void testGetOrderItemsId() throws Exception {
              Long orderItemsId = 1L;
              OrderItems orderItems = OrderItems.builder()
                      .id(orderItemsId)
                      .order_id(123L)
                      .created_at(LocalDateTime.now())
                      .modified_at(LocalDateTime.now())
                      .build();
              when(orderItemsService.getOrderItemsById(orderItemsId)).thenReturn(orderItems);

              mockMvc.perform(get("/order/{id}/items", orderItemsId)
                              .accept(MediaType.APPLICATION_JSON))
                      .andExpect(status().isOk())
                      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                      .andExpect(jsonPath("$.id").value(orderItemsId));
       }

       @Test
       public void testAddOrderItemsById() throws Exception {
              Long productId = 1L;
              OrderItems orderItems = new OrderItems();
              when(orderItemsService.addOrderItems(productId)).thenReturn(orderItems);


              mockMvc.perform(post("/post/{id}/order/items", productId)
                              .accept(MediaType.APPLICATION_JSON))
                      .andExpect(status().isOk())
                      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                      .andExpect(jsonPath("$.id").value(orderItems.getId()));
       }

       @Test
       public void testUpdateOrderItems() throws Exception {
              Long orderItemsId = 1L;
              OrderItems orderItems = new OrderItems();
              when(orderItemsService.updateOrderItemsById(any(OrderItems.class), any(Long.class))).thenReturn(orderItems);

              mockMvc.perform(post("/update/order/{id}/items", orderItemsId)
                              .content("{}")
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON))
                      .andExpect(status().isOk())
                      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                      .andExpect(jsonPath("$.id").value(orderItems.getId()));
       }

       @Test
       public void testDeleteOrderItems() throws Exception {
              Long orderItemsId = 1L;

              mockMvc.perform(delete("/order/{id}/items", orderItemsId))
                      .andExpect(status().isOk())
                      .andExpect(jsonPath("$").value("Order Items successfully deleted!"));
       }

}