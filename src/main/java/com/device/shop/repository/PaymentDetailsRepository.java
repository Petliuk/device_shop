package com.device.shop.repository;

import com.device.shop.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long> {

}