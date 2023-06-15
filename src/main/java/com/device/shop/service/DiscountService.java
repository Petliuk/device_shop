package com.device.shop.service;

import com.device.shop.entity.Discount;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.DiscountRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountService implements DiscountServiceInterface {

    DiscountRepository discountRepository;

    @Transactional
    public Discount addNewDiscount(Discount discount)  {
        return discountRepository.save(discount);
    }

    @Transactional
    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    @Transactional
    public Discount getDiscountById(Long discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount with id " + discountId + " not found"));
    }

    @Transactional
    public Discount updateDiscountById(Discount discount, Long discountId) throws BadRequestException, EntityNotFoundException {
        if (discountId == null || !discountRepository.existsById(discountId)) {
            throw new EntityNotFoundException("Discount with id " + discountId + " not found");
        } else if (!discountId.equals(discount.getId())) {
            throw new BadRequestException("Cannot change the id to " + discount.getId());
        } else {
            return discountRepository.save(discount);
        }
    }

    @Transactional
    public void deleteDiscount (Long discountId){
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
        } else {
            throw new EntityNotFoundException("Discount with id " + discountId + " not found");
        }
    }

}