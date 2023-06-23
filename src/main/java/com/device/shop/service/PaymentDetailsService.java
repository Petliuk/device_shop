package com.device.shop.service;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.model.PaymentDetailsDTO;

import javax.persistence.EntityNotFoundException;

public interface PaymentDetailsService {


    PaymentDetailsDTO createPaymentById(Long id, PaymentDetailsDTO paymentDTO);

    PaymentDetailsDTO getPaymentById(Long paymentId);

    PaymentDetailsDTO updatePaymentDetails(PaymentDetailsDTO paymentDetailsDTO, Long id) throws BadRequestException, EntityNotFoundException;

    void deletePaymentDetailsById(Long paymentId);

}