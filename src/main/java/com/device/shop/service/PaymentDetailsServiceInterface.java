package com.device.shop.service;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.exception.BadRequestException;

import javax.persistence.EntityNotFoundException;

public interface PaymentDetailsServiceInterface {

    PaymentDetails createPaymentById(Long id);

    PaymentDetails getPaymentById(Long paymentId);

    PaymentDetails updatePaymentDetails(PaymentDetails paymentDetails, Long id) throws BadRequestException, EntityNotFoundException;

    void deletePaymentDetailsById(Long paymentId);

}