package com.device.shop.service;


import com.device.shop.entity.PaymentDetails;
import com.device.shop.exception.BadRequestException;
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
        return paymentDetailsRepository.findById(paymentId).orElseThrow(() -> new EntityNotFoundException("Payment not found"));

    }

    @Transactional
    public PaymentDetails updatePaymentDetails(PaymentDetails paymentDetails, Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || !paymentDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment Details with id " + id + " not found");
        } else if (!id.equals(paymentDetails.getId())) {
            throw new BadRequestException("Cannot change the id to " + paymentDetails.getId());
        } else {
            return paymentDetailsRepository.save(paymentDetails);
        }
    }

    @Transactional
    public void deletePaymentDetailsById(Long paymentId) {
        if (paymentDetailsRepository.existsById(paymentId)) {
            paymentDetailsRepository.deleteById(paymentId);
        } else {
            throw new EntityNotFoundException("Payment with id " + paymentId + " not found");
        }
    }

}