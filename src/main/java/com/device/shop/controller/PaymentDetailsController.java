package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.PaymentDetailsDTO;
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
    public ResponseEntity<PaymentDetailsDTO> createPaymentById(@PathVariable("paymentId") Long id, @RequestBody PaymentDetailsDTO paymentDTO) {
        PaymentDetailsDTO createdPayment = paymentDetailsService.createPaymentById(id, paymentDTO);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("/payment-data/{paymentId}")
    public ResponseEntity<PaymentDetailsDTO> getPaymentDataById(@PathVariable("paymentId") Long paymentId) {
        PaymentDetailsDTO paymentDTO = paymentDetailsService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentDetailsDTO> updatePaymentDetails(@PathVariable("id") Long id, @RequestBody PaymentDetailsDTO paymentDetailsDTO) throws BadRequestException {
        PaymentDetailsDTO updatedPaymentDetailsDTO = paymentDetailsService.updatePaymentDetails(paymentDetailsDTO, id);
        return ResponseEntity.ok(updatedPaymentDetailsDTO);
    }

    @DeleteMapping("/payment/{id}/details")
    public ResponseEntity<String> deletePaymentDetailsById(@PathVariable("id") Long id) {
        paymentDetailsService.deletePaymentDetailsById(id);
        return new ResponseEntity<>("Payment Details successfully deleted!", HttpStatus.OK);
    }

}