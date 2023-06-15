package com.device.shop.test.controller;

import com.device.shop.controller.PaymentDetailsController;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.device.shop.entity.PaymentDetails;
import com.device.shop.service.PaymentDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PaymentDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentDetailsService paymentDetailsService;

    @InjectMocks
    private PaymentDetailsController paymentDetailsController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentDetailsController).build();
    }

    @Test
    void testCreatePaymentById() throws Exception {
        Long paymentId = 1L;
        PaymentDetails payment = new PaymentDetails(paymentId);
        payment.setId(paymentId);

        when(paymentDetailsService.createPaymentById(eq(paymentId))).thenReturn(payment);

        mockMvc.perform(post("/{paymentId}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentId));
    }

    @Test
    void testGetPaymentById() throws Exception {
        Long paymentId = 1L;
        PaymentDetails payment = new PaymentDetails(paymentId);
        payment.setId(paymentId);
        when(paymentDetailsService.getPaymentById(eq(paymentId))).thenReturn(payment);

        mockMvc.perform(get("/{paymentId}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentId));
    }

    @Test
    public void testUpdateOrderDetails() throws Exception {
        Long paymentDetailsId = 1L;
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setId(paymentDetailsId);

        when(paymentDetailsService.updatePaymentDetails(any(PaymentDetails.class))).thenReturn(paymentDetails);

        mockMvc.perform(put("/{id}", paymentDetailsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentDetailsId));

        verify(paymentDetailsService).updatePaymentDetails(any(PaymentDetails.class));
    }

    @Test
    public void testDeletePaymentDetailsById() throws Exception {
        Long paymentId = 1L;

        mockMvc.perform(delete("/payment{id}/details", paymentId))
                .andExpect(status().isNoContent());

        verify(paymentDetailsService, times(1)).deletePaymentDetailsById(paymentId);
    }

}