package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.OrderDetailsDTO;
import com.device.shop.service.impl.OrderDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsImpl orderDetailsService;

    @GetMapping("/order/{id}/details")
    public ResponseEntity<OrderDetailsDTO> getDetailsAboutTheOrderDetailsById(@PathVariable("id") Long orderId) {
        OrderDetailsDTO orderDetailsDTO = orderDetailsService.getOrderDetailsById(orderId);
        return new ResponseEntity<>(orderDetailsDTO, HttpStatus.OK);
    }

    @PostMapping("/order/details")
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) {
        OrderDetailsDTO savedOrderDetailsDTO = orderDetailsService.createOrder(orderDetailsDTO);
        return new ResponseEntity<>(savedOrderDetailsDTO, HttpStatus.CREATED);
    }

    @PutMapping("/order/{id}/details")
    public ResponseEntity<OrderDetailsDTO> updateOrderDetailsById(@PathVariable("id") Long orderDetailsId, @RequestBody OrderDetailsDTO orderDetailsDTO) throws BadRequestException {
        OrderDetailsDTO updatedOrderDetailsDTO = orderDetailsService.updateOrderDetailsById(orderDetailsDTO, orderDetailsId);
        return new ResponseEntity<>(updatedOrderDetailsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}/details")
    public ResponseEntity<String> deleteOrderDetailsById(@PathVariable("id") Long orderDetailsId) {
        orderDetailsService.deleteOrderDetailsById(orderDetailsId);
        return new ResponseEntity<>("Order Details successfully deleted!", HttpStatus.OK);
    }

}