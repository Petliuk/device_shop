package com.device.shop.test.service;

import com.device.shop.entity.Discount;
import com.device.shop.exception.BadRequestException;
import com.device.shop.repository.DiscountRepository;
import com.device.shop.service.DiscountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNewDiscount() {
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("Test Discount");
        discount.setDescription("Test Description");
        discount.setDiscount_percent("10");

        when(discountRepository.save(any(Discount.class))).thenReturn(discount);

        Discount result = discountService.addNewDiscount(discount);

        Assertions.assertEquals(discount.getId(), result.getId());
        Assertions.assertEquals(discount.getName(), result.getName());
        Assertions.assertEquals(discount.getDescription(), result.getDescription());
        Assertions.assertEquals(discount.getDiscount_percent(), result.getDiscount_percent());

        verify(discountRepository).save(any(Discount.class));
    }

    @Test
    public void testGetAllDiscount() {
        List<Discount> discounts = new ArrayList<>();
        Discount discount1 = new Discount();
        discount1.setId(1L);
        discount1.setName("Discount 1");
        discount1.setDescription("Description 1");
        discount1.setDiscount_percent("10");
        discounts.add(discount1);

        Discount discount2 = new Discount();
        discount2.setId(2L);
        discount2.setName("Discount 2");
        discount2.setDescription("Description 2");
        discount2.setDiscount_percent("15");
        discounts.add(discount2);

        when(discountRepository.findAll()).thenReturn(discounts);

        List<Discount> result = discountService.getAllDiscount();

        Assertions.assertEquals(discounts.size(), result.size());
        Assertions.assertEquals(discounts.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(discounts.get(1).getId(), result.get(1).getId());

        verify(discountRepository).findAll();
    }

    @Test
    public void testGetDiscountById() {
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("Test Discount");
        discount.setDescription("Test Description");
        discount.setDiscount_percent("10");

        when(discountRepository.findById(1L)).thenReturn(Optional.of(discount));

        Discount result = discountService.getDiscountById(1L);

        Assertions.assertEquals(discount.getId(), result.getId());
        Assertions.assertEquals(discount.getName(), result.getName());
        Assertions.assertEquals(discount.getDescription(), result.getDescription());
        Assertions.assertEquals(discount.getDiscount_percent(), result.getDiscount_percent());

        verify(discountRepository).findById(1L);
    }

    @Test
    public void testUpdateDiscountById() throws BadRequestException, EntityNotFoundException {
        Discount existingDiscount = new Discount();
        existingDiscount.setId(1L);
        existingDiscount.setName("Existing Discount");
        existingDiscount.setDescription("Existing Description");
        existingDiscount.setDiscount_percent("10");

        Discount updatedDiscount = new Discount();
        updatedDiscount.setId(1L);
        updatedDiscount.setName("Updated Discount");
        updatedDiscount.setDescription("Updated Description");
        updatedDiscount.setDiscount_percent("15");

        when(discountRepository.existsById(1L)).thenReturn(true);
        when(discountRepository.save(any(Discount.class))).thenReturn(updatedDiscount);

        Discount result = discountService.updateDiscountById(updatedDiscount, 1L);

        Assertions.assertEquals(updatedDiscount.getId(), result.getId());
        Assertions.assertEquals(updatedDiscount.getName(), result.getName());
        Assertions.assertEquals(updatedDiscount.getDescription(), result.getDescription());
        Assertions.assertEquals(updatedDiscount.getDiscount_percent(), result.getDiscount_percent());

        verify(discountRepository).existsById(1L);
        verify(discountRepository).save(any(Discount.class));
    }

    @Test
    public void testDeleteDiscount() {
        Long discountId = 1L;

        when(discountRepository.existsById(discountId)).thenReturn(true);

        discountService.deleteDiscount(discountId);

        verify(discountRepository).existsById(discountId);
        verify(discountRepository).deleteById(discountId);
    }

}