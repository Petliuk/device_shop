package com.device.shop.test.service;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.PaymentDetailsRepository;
import com.device.shop.service.PaymentDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentDetailsServiceTest {

    @Mock
    private PaymentDetailsRepository paymentDetailsRepository;

    @InjectMocks
    private PaymentDetailsService paymentDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreatePaymentById() {
        Long id = 1L;
        PaymentDetails payment = PaymentDetails.builder().id(id).build();
        when(paymentDetailsRepository.save(any(PaymentDetails.class))).thenReturn(payment);

        PaymentDetails result = paymentDetailsService.createPaymentById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(paymentDetailsRepository, times(1)).save(any(PaymentDetails.class));
    }

    @Test
    public void testGetPaymentById() {
        Long paymentId = 1L;
        PaymentDetails payment = PaymentDetails.builder().id(paymentId).build();
        when(paymentDetailsRepository.findById(paymentId)).thenReturn(java.util.Optional.of(payment));

        PaymentDetails result = paymentDetailsService.getPaymentById(paymentId);

        assertNotNull(result);
        assertEquals(paymentId, result.getId());
        verify(paymentDetailsRepository, times(1)).findById(paymentId);
    }

    @Test
    public void testUpdatePaymentDetails() throws BadRequestException {
        Long paymentDetailsId = 1L;
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setId(paymentDetailsId);
        when(paymentDetailsRepository.existsById(paymentDetailsId)).thenReturn(true);
        when(paymentDetailsRepository.save(paymentDetails)).thenReturn(paymentDetails);

        PaymentDetails result = paymentDetailsService.updatePaymentDetails(paymentDetails, paymentDetailsId);

        assertEquals(paymentDetails, result);
        verify(paymentDetailsRepository, times(1)).save(paymentDetails);
    }

    @Test
    public void testDeletePaymentDetailsById() {
        Long paymentId = 1L;

        when(paymentDetailsRepository.existsById(paymentId)).thenReturn(true);

        paymentDetailsService.deletePaymentDetailsById(paymentId);

        verify(paymentDetailsRepository).existsById(paymentId);
        verify(paymentDetailsRepository).deleteById(paymentId);
    }

}