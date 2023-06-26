package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.DiscountDTO;
import com.device.shop.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/discount")
    public ResponseEntity<DiscountDTO> addDiscount(@RequestBody DiscountDTO discountDTO) {
        DiscountDTO addedDiscountDTO = discountService.addNewDiscount(discountDTO);
        return new ResponseEntity<>(addedDiscountDTO, HttpStatus.CREATED);
    }

    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts() {
        List<DiscountDTO> discountDTOs = discountService.getAllDiscounts();
        return new ResponseEntity<>(discountDTOs, HttpStatus.OK);
    }

    @GetMapping("/discount/{id}")
    public ResponseEntity<DiscountDTO> getDetailsAboutTheDiscountById(@PathVariable("id") Long discountId) {
        DiscountDTO discountDTO = discountService.getDiscountById(discountId);
        return new ResponseEntity<>(discountDTO, HttpStatus.OK);
    }

    @PutMapping("/discount/{id}")
    public ResponseEntity<DiscountDTO> updateDiscountById(@PathVariable("id") Long discountId,
                                                          @RequestBody DiscountDTO discountDTO) throws BadRequestException, EntityNotFoundException {
        DiscountDTO updatedDiscountDTO = discountService.updateDiscountById(discountId, discountDTO);
        return new ResponseEntity<>(updatedDiscountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable("id") Long discountId) {
        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>("Discount successfully deleted!", HttpStatus.OK);
    }

}