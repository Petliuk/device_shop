package com.device.shop.test.service;

import com.device.shop.controller.CartItemController;
import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;
import com.device.shop.entity.ShoppingSession;
import com.device.shop.mapper.CartItemMapper;
import com.device.shop.mapper.ProductMapper;
import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.CartItemRepository;
import com.device.shop.repository.ShoppingSessionRepository;
import com.device.shop.service.ProductService;
import com.device.shop.service.impl.CartItemImpl;
import com.device.shop.service.impl.ProductImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class CartItemServiceTest {

/*    private CartItemImpl cartItemService;

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductImpl productService;
    @Mock
    private ShoppingSessionRepository shoppingSessionRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cartItemService = new CartItemImpl(cartItemRepository, null, shoppingSessionRepository);
    }

    // Дописати тест addToCart

    @Test
    void testGetProductsInCart() {
        Long cartId = 1L;

        CartItem cartItem1 = CartItem.builder()
                .product(new Product())
                .build();

        CartItem cartItem2 = CartItem.builder()
                .product(new Product())
                .build();

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        when(cartItemRepository.findAllByShoppingSessionId(cartId)).thenReturn(cartItems);

        List<ProductDTO> result = cartItemService.getProductsInCart(cartId);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(cartItemRepository, times(1)).findAllByShoppingSessionId(cartId);
    }

    @Test
    void testDeleteTheProduct() {
        Long cartItemId = 1L;

        when(cartItemRepository.existsById(cartItemId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> cartItemService.deleteTheProduct(cartItemId));

        verify(cartItemRepository, times(1)).existsById(cartItemId);
        verify(cartItemRepository, never()).deleteById(cartItemId);
    }

    @Test
    void deleteTheProduct_ProductDoesNotExist_EntityNotFoundExceptionThrown() {

        Long cartId = 1L;
        when(cartItemRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> cartItemService.deleteTheProduct(cartId));
        verify(cartItemRepository, never()).deleteById(anyLong());
    }

    @Test
    void removeAllProductsFromCart_CartItemsDeleted() {

        Long cartId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        when(cartItemRepository.findAllByShoppingSessionId(anyLong())).thenReturn(cartItems);

        cartItemService.removeAllProductsFromCart(cartId);

        verify(cartItemRepository, times(1)).deleteAll(cartItems);
    }*/

}