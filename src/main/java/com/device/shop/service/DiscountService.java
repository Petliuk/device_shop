package com.device.shop.service;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.DiscountDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface DiscountService {

    DiscountDTO addNewDiscount(DiscountDTO discountDTO);

    List<DiscountDTO> getAllDiscounts();

    DiscountDTO getDiscountById(Long discountId);

    DiscountDTO updateDiscountById(Long discountId, DiscountDTO discountDTO) throws BadRequestException, EntityNotFoundException;

    void deleteDiscount(Long discountId);

}