package com.device.shop.test.controller;

import com.device.shop.controller.PaymentDetailsController;
import com.device.shop.exception.BadRequestException;
import com.device.shop.model.PaymentDetailsDTO;
import com.device.shop.service.PaymentDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

public class PaymentDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentDetailsService paymentDetailsService;

    @InjectMocks
    private PaymentDetailsController paymentDetailsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentDetailsController).build();
    }

/*
    @Test
    void testCreatePaymentById() throws Exception {
        Long paymentId = 1L;
        PaymentDetailsDTO paymentDTO = new PaymentDetailsDTO();
        PaymentDetailsDTO createdPayment = new PaymentDetailsDTO();
        createdPayment.setId(paymentId);

        when(paymentDetailsService.createPaymentById(eq(paymentId), any(PaymentDetailsDTO.class)))
                .thenReturn(createdPayment);

        mockMvc.perform(post("/{paymentId}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentId));

        verify(paymentDetailsService, times(1)).createPaymentById(paymentId, paymentDTO);
    }
*/

    @Test
    void testGetPaymentDataById() throws Exception {

        Long paymentId = 1L;
        PaymentDetailsDTO paymentDTO = new PaymentDetailsDTO();
        paymentDTO.setId(paymentId);

        when(paymentDetailsService.getPaymentById(paymentId)).thenReturn(paymentDTO);


        mockMvc.perform(get("/payment-data/{paymentId}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentId));

        verify(paymentDetailsService, times(1)).getPaymentById(paymentId);
    }

 /*   @Test
    void testUpdatePaymentDetails() throws Exception {

        PaymentDetailsDTO paymentDTO = new PaymentDetailsDTO();
        paymentDTO.setId(1L);

        when(paymentDetailsService.updatePaymentDetails(any(PaymentDetailsDTO.class), eq(1L)))
                .thenReturn(paymentDTO);

        mockMvc.perform(put("/payment/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(paymentDetailsService, times(1)).updatePaymentDetails(eq(paymentDTO), eq(1L));
    }*/

    @Test
    void testDeletePaymentDetailsById() throws Exception {
        Long paymentId = 1L;

        mockMvc.perform(delete("/payment/{id}/details", paymentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Details successfully deleted!"));

        verify(paymentDetailsService, times(1)).deletePaymentDetailsById(paymentId);
    }
}
