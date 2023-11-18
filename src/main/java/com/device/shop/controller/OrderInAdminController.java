package com.device.shop.controller;

import com.device.shop.model.OrderDTO;
import com.device.shop.service.impl.OrderInAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
public class OrderInAdminController {
    private final OrderInAdminService orderInAdminService;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderInAdminService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderInAdminService.getAllOrdersWithDetails();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



}

