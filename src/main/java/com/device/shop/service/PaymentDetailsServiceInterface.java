package com.device.shop.service;

import com.device.shop.entity.PaymentDetails;

public interface PaymentDetailsServiceInterface {

    PaymentDetails createPaymentById(Long id);
    PaymentDetails getPaymentById(Long paymentId);
    PaymentDetails updatePaymentDetails (PaymentDetails paymentDetails);
    void deletePaymentDetailsById(Long paymentId);

}