package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.PaymentDetailsDTO;
import com.device.shop.service.PaymentDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{paymentId}")
    public ResponseEntity<PaymentDetailsDTO> createPaymentById(@PathVariable("paymentId") Long id, @RequestBody PaymentDetailsDTO paymentDTO) {
        PaymentDetailsDTO createdPayment = paymentDetailsService.createPaymentById(id, paymentDTO);
        return ResponseEntity.ok(createdPayment);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/payment-data/{paymentId}")
    public ResponseEntity<PaymentDetailsDTO> getPaymentDataById(@PathVariable("paymentId") Long paymentId) {
        PaymentDetailsDTO paymentDTO = paymentDetailsService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentDetailsDTO> updatePaymentDetails(@PathVariable("id") Long id, @RequestBody PaymentDetailsDTO paymentDetailsDTO) throws BadRequestException {
        PaymentDetailsDTO updatedPaymentDetailsDTO = paymentDetailsService.updatePaymentDetails(paymentDetailsDTO, id);
        return ResponseEntity.ok(updatedPaymentDetailsDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/payment/{id}/details")
    public ResponseEntity<String> deletePaymentDetailsById(@PathVariable("id") Long id) {
        paymentDetailsService.deletePaymentDetailsById(id);
        return new ResponseEntity<>("Payment Details successfully deleted!", HttpStatus.OK);
    }

}