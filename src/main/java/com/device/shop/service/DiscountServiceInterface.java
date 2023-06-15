package com.device.shop.service;

import com.device.shop.entity.Discount;
import com.device.shop.exception.BadRequestException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface DiscountServiceInterface {

    Discount addNewDiscount(Discount discount);
    List<Discount> getAllDiscount();
    Discount getDiscountById(Long discountId);
    Discount updateDiscountById(Discount discount, Long discountId)throws BadRequestException, EntityNotFoundException;
    void deleteDiscount (Long discountId);

}