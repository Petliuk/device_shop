package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.OrderDetailsDTO;
import com.device.shop.service.OrderDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/order/{id}/details")
    public ResponseEntity<OrderDetailsDTO> getDetailsAboutTheOrderDetailsById(@PathVariable("id") Long orderId) {
        OrderDetailsDTO orderDetailsDTO = orderDetailsService.getOrderDetailsById(orderId);
        return new ResponseEntity<>(orderDetailsDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/order/details")
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) {
        OrderDetailsDTO savedOrderDetailsDTO = orderDetailsService.createOrder(orderDetailsDTO);
        return new ResponseEntity<>(savedOrderDetailsDTO, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/order/{id}/details")
    public ResponseEntity<OrderDetailsDTO> updateOrderDetailsById(@PathVariable("id") Long orderDetailsId, @RequestBody OrderDetailsDTO orderDetailsDTO) throws BadRequestException {
        OrderDetailsDTO updatedOrderDetailsDTO = orderDetailsService.updateOrderDetailsById(orderDetailsDTO, orderDetailsId);
        return new ResponseEntity<>(updatedOrderDetailsDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/order/{id}/details")
    public ResponseEntity<String> deleteOrderDetailsById(@PathVariable("id") Long orderDetailsId) {
        orderDetailsService.deleteOrderDetailsById(orderDetailsId);
        return new ResponseEntity<>("Order Details successfully deleted!", HttpStatus.OK);
    }

}