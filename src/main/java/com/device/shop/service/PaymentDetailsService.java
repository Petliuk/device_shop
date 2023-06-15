package com.device.shop.service;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.repository.PaymentDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class PaymentDetailsService implements PaymentDetailsServiceInterface {

    PaymentDetailsRepository paymentDetailsRepository;
    @Transactional
    public PaymentDetails createPaymentById(Long id) {
        PaymentDetails payment = new PaymentDetails(id);
        return paymentDetailsRepository.save(payment);
    }


    @Transactional
    public PaymentDetails getPaymentById(Long paymentId) {
        return paymentDetailsRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

    }

    @Transactional
    public PaymentDetails updatePaymentDetails (PaymentDetails paymentDetails) {
        return paymentDetailsRepository.save(paymentDetails);
    }
    @Transactional
    public void deletePaymentDetailsById(Long paymentId) {
        paymentDetailsRepository.deleteById(paymentId);
    }

}