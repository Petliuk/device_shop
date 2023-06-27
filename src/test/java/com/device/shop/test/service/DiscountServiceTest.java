package com.device.shop.test.service;

import com.device.shop.entity.Discount;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.DiscountMapper;
import com.device.shop.model.DiscountDTO;
import com.device.shop.repository.DiscountRepository;
import com.device.shop.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {


    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private DiscountMapper discountMapper;

    @InjectMocks
    private DiscountServiceImpl discountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNewDiscount() {
        DiscountDTO discountDTO = DiscountDTO.builder()
                .id(1L)
                .name("Test Discount")
                .build();

        Discount discount = Discount.builder()
                .id(discountDTO.getId())
                .name(discountDTO.getName())
                .build();

        when(discountMapper.toEntity(discountDTO)).thenReturn(discount);
        when(discountMapper.toDTO(discount)).thenReturn(discountDTO);
        when(discountRepository.save(discount)).thenReturn(discount);

        DiscountDTO resultDTO = discountService.addNewDiscount(discountDTO);

        assertEquals(discountDTO.getId(), resultDTO.getId());
        assertEquals(discountDTO.getName(), resultDTO.getName());

        verify(discountRepository).save(discount);
        verify(discountMapper).toEntity(discountDTO);
        verify(discountMapper).toDTO(discount);
    }

    @Test
    void testGetAllDiscounts() {

        List<Discount> discounts = Arrays.asList(
                Discount.builder()
                        .id(1L)
                        .name("Discount 1")
                        .description("Description 1")
                        .discountPercent("10%")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build(),
                Discount.builder()
                        .id(2L)
                        .name("Discount 2")
                        .description("Description 2")
                        .discountPercent("20%")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build()
        );
        when(discountRepository.findAll()).thenReturn(discounts);

        List<DiscountDTO> expectedDiscountDTOs = Arrays.asList(
                DiscountDTO.builder()
                        .id(1L)
                        .name("Discount 1")
                        .description("Description 1")
                        .discountPercent("10%")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build(),
                DiscountDTO.builder()
                        .id(2L)
                        .name("Discount 2")
                        .description("Description 2")
                        .discountPercent("20%")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build()
        );
        when(discountMapper.toDTO(discounts.get(0))).thenReturn(expectedDiscountDTOs.get(0));
        when(discountMapper.toDTO(discounts.get(1))).thenReturn(expectedDiscountDTOs.get(1));

        List<DiscountDTO> result = discountService.getAllDiscounts();

        assertEquals(expectedDiscountDTOs, result);
        verify(discountRepository, times(1)).findAll();
        verify(discountMapper, times(1)).toDTO(discounts.get(0));
        verify(discountMapper, times(1)).toDTO(discounts.get(1));
    }

    //toDo add test getAllDiscounts_NotFound

    @Test
    void testGetDiscountById_ExistingId_ShouldReturnDiscountDTO() {

        Long discountId = 1L;
        Discount discount = Discount.builder()
                .id(discountId)
                .name("Discount 1")
                .description("Description 1")
                .discountPercent("10%")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        when(discountRepository.findById(discountId)).thenReturn(Optional.of(discount));

        DiscountDTO expectedDiscountDTO = DiscountDTO.builder()
                .id(discountId)
                .name("Discount 1")
                .description("Description 1")
                .discountPercent("10%")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        when(discountMapper.toDTO(discount)).thenReturn(expectedDiscountDTO);

        DiscountDTO result = discountService.getDiscountById(discountId);

        assertEquals(expectedDiscountDTO, result);
        verify(discountRepository, times(1)).findById(discountId);
        verify(discountMapper, times(1)).toDTO(discount);
    }

    @Test
    void testGetDiscountById_NonExistingDiscountId_ThrowsEntityNotFoundException() {

        Long discountId = 1L;
        when(discountRepository.findById(discountId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> discountService.getDiscountById(discountId));
        verify(discountRepository, times(1)).findById(discountId);
        verifyNoMoreInteractions(discountRepository);
    }


    @Test
    void testUpdateDiscountById() {

        Long discountId = 1L;
        DiscountDTO discountDTO = DiscountDTO.builder()
                .id(discountId)
                .name("New Discount")
                .description("Updated Description")
                .discountPercent("10%")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Discount updatedDiscount = Discount.builder()
                .id(discountId)
                .name("New Discount")
                .description("Updated Description")
                .discountPercent("10%")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Discount savedDiscount = Discount.builder()
                .id(discountId)
                .name("New Discount")
                .description("Updated Description")
                .discountPercent("10%")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        when(discountRepository.save(updatedDiscount)).thenReturn(savedDiscount);
        when(discountMapper.toEntity(discountDTO)).thenReturn(updatedDiscount);
        when(discountMapper.toDTO(savedDiscount)).thenReturn(discountDTO);

        DiscountDTO updatedDiscountDTO = null;
        try {
            updatedDiscountDTO = discountService.updateDiscountById(discountId, discountDTO);
        } catch (BadRequestException e) {
            fail("Should not throw a BadRequestException");
        }

        assertNotNull(updatedDiscountDTO);
        assertEquals(discountId, updatedDiscountDTO.getId());
        assertEquals("New Discount", updatedDiscountDTO.getName());
        assertEquals("Updated Description", updatedDiscountDTO.getDescription());
        assertEquals("10%", updatedDiscountDTO.getDiscountPercent());
        assertNotNull(updatedDiscountDTO.getCreatedAt());
        assertNotNull(updatedDiscountDTO.getModifiedAt());

        verify(discountRepository, times(1)).save(updatedDiscount);
        verify(discountMapper, times(1)).toEntity(discountDTO);
        verify(discountMapper, times(1)).toDTO(savedDiscount);
    }

    @Test
    void updateDiscountById_ThrowsBadRequestException() {
        Long discountId = 1L;
        DiscountDTO discountDTO = DiscountDTO.builder()
                .id(2L)
                .name("New Discount")
                .description("Updated Description")
                .discountPercent("10%")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        assertThrows(BadRequestException.class, () -> discountService.updateDiscountById(discountId, discountDTO));

        verify(discountRepository, never()).save(any());
        verify(discountMapper, never()).toEntity(any());
        verify(discountMapper, never()).toDTO(any());
    }

    //toDo add test updateDiscountById_NotFound

    @Test
    void deleteDiscount() {

        Long discountId = 1L;
        when(discountRepository.existsById(discountId)).thenReturn(true);

        discountService.deleteDiscount(discountId);

        verify(discountRepository, times(1)).existsById(discountId);
        verify(discountRepository, times(1)).deleteById(discountId);
        verifyNoMoreInteractions(discountRepository);
    }
    //toDo add test deleteDiscount_NotFound
}
