package com.device.shop.test.service;

import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;
import com.device.shop.repository.CartItemRepository;
import com.device.shop.repository.ProductRepository;
import com.device.shop.service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToCartValidProductIdCartItemCreated() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        CartItem cartItem = cartItemService.addToCart(productId);

        assertNotNull(cartItem);
        assertEquals(product, cartItem.getProduct());
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void addToCartInvalidProductIdEntityNotFoundExceptionThrown() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartItemService.addToCart(productId));
        verify(cartItemRepository, never()).save(any());
    }

    @Test
    void getProductsInCartValidCartIdProductsRetrieved() {
        Long cartId = 1L;
        CartItem cartItem = new CartItem();
        Product product = new Product();
        cartItem.setProduct(product);
        when(cartItemRepository.findById(cartId)).thenReturn(Optional.of(cartItem));

        List<Product> products = cartItemService.getProductsInCart(cartId);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void getProductsInCart_InvalidCartId_EntityNotFoundExceptionThrown() {

        Long cartId = 1L;
        when(cartItemRepository.findById(cartId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> cartItemService.getProductsInCart(cartId));
    }

    @Test
    void deleteTheProduct_ExistingCartId_CartItemDeleted() {
        Long cartId = 1L;
        when(cartItemRepository.existsById(cartId)).thenReturn(true);

        cartItemService.deleteTheProduct(cartId);

        verify(cartItemRepository, times(1)).deleteById(cartId);
    }

    @Test
    void deleteTheProduct_NonExistingCartId_EntityNotFoundExceptionThrown() {
        Long cartId = 1L;
        when(cartItemRepository.existsById(cartId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> cartItemService.deleteTheProduct(cartId));
        verify(cartItemRepository, never()).deleteById(any());
    }

    @Test
    public void testRemoveAllProductsFromCart() {
        Long cartId = 1L;

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1L);
        cartItem1.setProduct(null);
        cartItem1.setShoppingSession(null);

        CartItem cartItem2 = new CartItem();
        cartItem2.setId(2L);
        cartItem2.setProduct(null);
        cartItem2.setShoppingSession(null);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        when(cartItemRepository.findAllByShoppingSessionId(cartId)).thenReturn(cartItems);

        cartItemService.removeAllProductsFromCart(cartId);

        verify(cartItemRepository, times(1)).deleteAll(cartItems);
    }

}