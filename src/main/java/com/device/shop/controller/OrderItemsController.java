package com.device.shop.controller;

import com.device.shop.entity.OrderItems;
import com.device.shop.exception.BadRequestException;
import com.device.shop.service.OrderItemsService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class OrderItemsController {

    OrderItemsService orderItemsService;

    @GetMapping("/order/{id}/items")
    public ResponseEntity<OrderItems> getOrderItemsId(@PathVariable("id") Long orderItemsId) {
        OrderItems orderItems = orderItemsService.getOrderItemsById(orderItemsId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PostMapping("/post/{id}/order/items")
    public ResponseEntity<OrderItems> addOrderItemsById(@PathVariable("id") Long productId) {
        OrderItems orderItems = orderItemsService.addOrderItems(productId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PostMapping("/update/order/{id}/items")
    public ResponseEntity<OrderItems> updateOrderItems(@PathVariable("id") Long orderItemsId,
                                           @RequestBody OrderItems orderItems) throws BadRequestException {
        OrderItems updatedOrder = orderItemsService.updateOrderItemsById(orderItems, orderItemsId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}/items")
    public ResponseEntity<String> deleteOrderItems(@PathVariable("id") Long orderItemsId) {
        orderItemsService.deleteOrderItemsById(orderItemsId);
        return new ResponseEntity<>("Order Items successfully deleted!", HttpStatus.OK);
    }

}