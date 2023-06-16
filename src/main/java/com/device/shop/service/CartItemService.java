package com.device.shop.service;

import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;

import com.device.shop.repository.CartItemRepository;
import com.device.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartItemService implements CartItemServiceInterface {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItem addToCart(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);

        return cartItem;
    }

    @Transactional
    public List<Product> getProductsInCart(Long productId) {
        CartItem cartItem = cartItemRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + productId));

        List<Product> products = new ArrayList<>();
        products.add(cartItem.getProduct());

        return products;
    }

    @Transactional
    public void deleteTheProduct(Long cartId) {
        if (cartItemRepository.existsById(cartId)) {
            cartItemRepository.deleteById(cartId);
        } else {
            throw new EntityNotFoundException("Cart with id " + cartId + " not found");
        }
    }

    @Transactional
    public void removeAllProductsFromCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingSessionId(cartId);
        cartItemRepository.deleteAll(cartItems);
    }

}