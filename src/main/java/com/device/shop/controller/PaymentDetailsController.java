package com.device.shop.controller;


import com.device.shop.entity.PaymentDetails;
import com.device.shop.service.PaymentDetailsService;

import lombok.AllArgsConstructor;

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
    public ResponseEntity<PaymentDetails> getPaymentDetaById(@PathVariable("paymentId") Long paymentId) {
        PaymentDetails payment = paymentDetailsService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDetails> updateOrderDetails(@PathVariable("id") Long id, @RequestBody PaymentDetails paymentDetails) {
        paymentDetails.setId(id);
        PaymentDetails updatedOrderDetails = paymentDetailsService.updatePaymentDetails(paymentDetails);
        return ResponseEntity.ok(updatedOrderDetails);
    }

    @DeleteMapping("/payment{id}/details")
    public ResponseEntity<Void> deletePaymentDetailsById(@PathVariable("id") Long paymentId) {
        paymentDetailsService.deletePaymentDetailsById(paymentId);
        return ResponseEntity.noContent().build();
    }

}