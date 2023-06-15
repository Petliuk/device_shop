package com.device.shop.controller;

import com.device.shop.entity.OrderDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.service.OrderDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class OrderDetailsController {

    OrderDetailsService orderDetailsService;

    @GetMapping("/order/{id}/details")
    public ResponseEntity<OrderDetails> getDetailsAboutTheOderDerailsById (@PathVariable("id") Long orderId){
        OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @PostMapping("/order/{id}/details")
    public ResponseEntity<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails)  {
        OrderDetails savedOrderDetails = orderDetailsService.createOrder(orderDetails);
        return new ResponseEntity<>(savedOrderDetails, HttpStatus.CREATED);
    }

    @PutMapping ("/order/{id}/details")
    public ResponseEntity <OrderDetails> updateOrderDetailsById(@PathVariable("id") Long orderDetailsId,
                                                               @RequestBody OrderDetails orderDetails) throws BadRequestException {
        OrderDetails updateOrderDetails = orderDetailsService.updateOrderDetailsById(orderDetails,orderDetailsId);
        return new  ResponseEntity<>(updateOrderDetails, HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}/details")
    public ResponseEntity<String> deleteOrderDetailsById(@PathVariable("id") Long orderDetailsId) {
        orderDetailsService.deleteOrderDetailsById(orderDetailsId);
        return new ResponseEntity<>("Order Details successfully deleted!", HttpStatus.OK);
    }

}