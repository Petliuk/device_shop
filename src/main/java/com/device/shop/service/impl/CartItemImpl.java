package com.device.shop.service.impl;

import com.device.shop.entity.CartItem;

import com.device.shop.entity.Product;
import com.device.shop.entity.ShoppingSession;
import com.device.shop.entity.User;
import com.device.shop.mapper.CartItemMapper;
import com.device.shop.mapper.ProductMapper;
import com.device.shop.mapper.ShoppingSessionMapper;
import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.CartItemRepository;
import com.device.shop.repository.ShoppingSessionRepository;
import com.device.shop.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartItemImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductImpl productService;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;
    private final ShoppingSessionImpl shoppingSessionService;
    private  final ShoppingSessionMapper shoppingSessionMapper;

    @Transactional
    public CartItemDTO addToCart(Long shoppingId, CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);

        Product product = productMapper.toEntity(productService.getProductById(cartItemDTO.getProductId()));
        cartItem.setProduct(product);

        ShoppingSession shoppingSession = shoppingSessionMapper.toEntity(shoppingSessionService.getShoppingSessionById(shoppingId));
        cartItem.setShoppingSession(shoppingSession);

        CartItem newCartItem = cartItemRepository.save(cartItem);

        return cartItemMapper.toDTO(newCartItem);
    }

    @Transactional
    public List<ProductDTO> getProductsInCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingSessionId(cartId);
        List<ProductDTO> products = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            products.add(productMapper.toDTO(cartItem.getProduct()));
        }

        return products;
    }

    @Transactional
    public void deleteTheProduct(Long cartId) {
        if (cartItemRepository.existsById(cartId)) {
            cartItemRepository.deleteById(cartId);
        } else {
            throw new EntityNotFoundException("Cart item with id " + cartId + " not found");
        }
    }

    @Transactional
    public void removeAllProductsFromCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingSessionId(cartId);
        cartItemRepository.deleteAll(cartItems);
    }

}