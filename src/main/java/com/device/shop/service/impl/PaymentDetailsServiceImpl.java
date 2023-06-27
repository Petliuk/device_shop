package com.device.shop.service.impl;


import com.device.shop.entity.PaymentDetails;
import com.device.shop.exception.BadRequestException;
import com.device.shop.model.PaymentDetailsDTO;
import com.device.shop.repository.PaymentDetailsRepository;
import com.device.shop.service.PaymentDetailsService;
import com.device.shop.mapper.PaymentDetailsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    //toDo decouple
    PaymentDetailsRepository paymentDetailsRepository;
    PaymentDetailsMapper paymentDetailsMapper;

    @Transactional
    public PaymentDetailsDTO createPaymentById(Long id, PaymentDetailsDTO paymentDTO) {
        PaymentDetails payment = paymentDetailsMapper.toEntity(paymentDTO);
        payment.setId(id);
        PaymentDetails savedPayment = paymentDetailsRepository.save(payment);
        return paymentDetailsMapper.toDTO(savedPayment);
    }

    @Transactional
    public PaymentDetailsDTO getPaymentById(Long paymentId) {
        PaymentDetails payment = paymentDetailsRepository.findById(paymentId).orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return paymentDetailsMapper.toDTO(payment);
    }

    @Transactional
    public PaymentDetailsDTO updatePaymentDetails(PaymentDetailsDTO paymentDetailsDTO, Long id) throws BadRequestException, EntityNotFoundException {
        PaymentDetails paymentDetails = paymentDetailsMapper.toEntity(paymentDetailsDTO);
        if (id == null || !paymentDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment Details with id " + id + " not found");
        } else if (!id.equals(paymentDetails.getId())) {
            throw new BadRequestException("Cannot change the id to " + paymentDetails.getId());
        } else {
            PaymentDetails updatedPaymentDetails = paymentDetailsRepository.save(paymentDetails);
            return paymentDetailsMapper.toDTO(updatedPaymentDetails);
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