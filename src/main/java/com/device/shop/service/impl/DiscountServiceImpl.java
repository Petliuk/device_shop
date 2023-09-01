package com.device.shop.service.impl;


import com.device.shop.entity.Discount;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.DiscountMapper;
import com.device.shop.model.DiscountDTO;
import com.device.shop.repository.DiscountRepository;
import com.device.shop.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    //toDo decouple
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Transactional
    public DiscountDTO addNewDiscount(DiscountDTO discountDTO) {
        Discount discount = discountMapper.toEntity(discountDTO);
        Discount addedDiscount = discountRepository.save(discount);
        return discountMapper.toDTO(addedDiscount);
    }

    @Transactional
    public List<DiscountDTO> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(discountMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public DiscountDTO getDiscountById(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount with id " + discountId + " not found"));
        return discountMapper.toDTO(discount);
    }


    @Transactional
    public DiscountDTO updateDiscountById(Long discountId, DiscountDTO discountDTO) throws BadRequestException {

        if (!discountId.equals(discountDTO.getId())) {
            throw new BadRequestException("Cannot change the id to " + discountDTO.getId());
        }

        Discount updatedDiscount = discountMapper.toEntity(discountDTO);
        updatedDiscount.setId(discountId);

        Discount savedDiscount = discountRepository.save(updatedDiscount);
        return discountMapper.toDTO(savedDiscount);
    }

    @Transactional
    public void deleteDiscount(Long discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
        } else {
            throw new EntityNotFoundException("Discount with id " + discountId + " not found");
        }

    }

}