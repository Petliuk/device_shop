package com.device.shop.service.impl;

import com.device.shop.entity.CartItem;

import com.device.shop.entity.Product;
import com.device.shop.entity.ShoppingSession;
import com.device.shop.mapper.CartItemMapper;
import com.device.shop.mapper.ProductMapper;
import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.repository.CartItemRepository;
import com.device.shop.repository.ProductRepository;
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
public class CartItemServiceImpl implements CartItemService {

    //toDo decouple
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItemDTO addToCart(Long shoppingId, CartItemDTO cartItemDTO) {
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + cartItemDTO.getProductId() + " not found"));

        ShoppingSession shoppingSession = shoppingSessionRepository.findById(shoppingId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping Session with id " + shoppingId + " not found"));

        CartItem existingCartItem = cartItemRepository.findByProductAndShoppingSession(product, shoppingSession);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            existingCartItem.setModifiedAt(LocalDateTime.now());
            cartItemRepository.save(existingCartItem);

            cartItemDTO.setQuantity(existingCartItem.getQuantity());

            return cartItemDTO;
        } else {
            CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
            cartItem.setId(cartItemDTO.getId());
            cartItem.setQuantity(cartItem.getQuantity());
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setModifiedAt(LocalDateTime.now());
            cartItem.setProduct(product);
            cartItem.setShoppingSession(shoppingSession);

            CartItem newCartItem = cartItemRepository.save(cartItem);

            cartItemDTO.setQuantity(newCartItem.getQuantity());

            return cartItemMapper.toDTO(newCartItem);
        }
    }

    @Transactional
    public List<ProductDTO> getProductsInCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingSessionId(cartId);
        List<ProductDTO> products = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductDTO productDTO = productMapper.toDTO(cartItem.getProduct());
            productDTO.setQuantity(cartItem.getQuantity());
            productDTO.setId(cartItem.getId());
            products.add(productDTO);
        }

        return products;
    }

    @Transactional
    public void deleteTheCartItem(Long cartItemId) {
        if (cartItemRepository.existsById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new EntityNotFoundException("Cart item with id " + cartItemId + " not found");
        }
    }

    @Transactional
    public void removeAllProductsFromCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingSessionId(cartId);
        cartItemRepository.deleteAll(cartItems);
    }

}