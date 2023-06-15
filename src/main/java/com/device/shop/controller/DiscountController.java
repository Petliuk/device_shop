package com.device.shop.controller;

import com.device.shop.entity.Discount;
import com.device.shop.exception.BadRequestException;
import com.device.shop.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class DiscountController {

    DiscountService discountService;

    @PostMapping("/discount")
    public ResponseEntity<Discount> addDiscount(@RequestBody Discount discount)  {
        Discount addDiscount = discountService.addNewDiscount(discount);
        return new ResponseEntity<>(addDiscount, HttpStatus.CREATED);
    }

    @GetMapping("get/discount")
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscount();
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @GetMapping("/discount/{id}")
    public ResponseEntity <Discount> getDetailsAboutTheDiscountById (@PathVariable("id") Long discountId){
       Discount discount = discountService.getDiscountById(discountId);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }

    @PostMapping("/discount/{id}")
    public ResponseEntity <Discount> updateDiscountInformation(@PathVariable("id") Long discountId,
                                                               @RequestBody Discount discount) throws BadRequestException {
        Discount updateDiscount = discountService.updateDiscountById(discount,discountId);
        return new  ResponseEntity<>(updateDiscount, HttpStatus.OK);
    }

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<String> deleteDiscount (@PathVariable("id") Long discountId){
        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>("Discount successfully deleted!", HttpStatus.OK);
    }

}