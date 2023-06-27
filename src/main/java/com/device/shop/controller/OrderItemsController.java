package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;;
import com.device.shop.model.OrderItemsDTO;
import com.device.shop.service.OrderItemsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/order/{id}/items")
    public ResponseEntity<OrderItemsDTO> getOrderItemsById(@PathVariable("id") Long orderItemsId) {
        OrderItemsDTO orderItemsDTO = orderItemsService.getOrderItemsById(orderItemsId);
        return new ResponseEntity<>(orderItemsDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/order/{id}/items")
    public ResponseEntity<OrderItemsDTO> addOrderItemsById(@PathVariable("id") Long productId, @RequestBody OrderItemsDTO orderItemsDTO) {
        OrderItemsDTO createdOrderItems = orderItemsService.addOrderItems(productId, orderItemsDTO);
        return new ResponseEntity<>(createdOrderItems, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/order/items/{id}")
    public ResponseEntity<OrderItemsDTO> updateOrderItemsById(@PathVariable("id") Long orderItemsId, @RequestBody OrderItemsDTO orderItemsDTO) throws BadRequestException {
        OrderItemsDTO updatedOrderItemsDTO = orderItemsService.updateOrderItemsById(orderItemsDTO, orderItemsId);
        return new ResponseEntity<>(updatedOrderItemsDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/order/{id}/items")
    public ResponseEntity<String> deleteOrderItemsById(@PathVariable("id") Long orderItemsId) {
        orderItemsService.deleteOrderItemsById(orderItemsId);
        return new ResponseEntity<>("Order Items successfully deleted!", HttpStatus.OK);
    }

}