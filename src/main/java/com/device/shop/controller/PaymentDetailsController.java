package com.device.shop.controller;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.service.PaymentDetailsService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class PaymentDetailsController {

    PaymentDetailsService paymentDetailsService;

    @PostMapping("/{paymentId}")
    public ResponseEntity<PaymentDetails> createPaymentById(@PathVariable("paymentId") Long id) {
        PaymentDetails payment = paymentDetailsService.createPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDetails> getPaymentDataById(@PathVariable("paymentId") Long paymentId) {
        PaymentDetails payment = paymentDetailsService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentDetails> updatePaymentDetails(@PathVariable("id") Long id, @RequestBody PaymentDetails paymentDetails) throws BadRequestException {
        PaymentDetails updatedOrderDetails = paymentDetailsService.updatePaymentDetails(paymentDetails, id);
        return ResponseEntity.ok(updatedOrderDetails);
    }

    @DeleteMapping("/payment/{id}/details")
    public ResponseEntity<String> deletePaymentDetailsById(@PathVariable("id") Long id) {
        paymentDetailsService.deletePaymentDetailsById(id);
        return new ResponseEntity<>("Payment Details successfully deleted!", HttpStatus.OK);
    }

}