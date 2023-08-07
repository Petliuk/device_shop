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
import org.springframework.dao.DataIntegrityViolationException;
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

        // Перевіряємо наявність продукту в корзині за допомогою product.getId() і shoppingSession.getId()
        CartItem existingCartItem = cartItemRepository.findByProductAndShoppingSession(product, shoppingSession);

        if (existingCartItem != null) {
            // Якщо продукт вже є в корзині, оновлюємо кількість продукту
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            existingCartItem.setModifiedAt(LocalDateTime.now());
            cartItemRepository.save(existingCartItem);

            // Оновити кількість у CartItemDTO перед поверненням
            cartItemDTO.setQuantity(existingCartItem.getQuantity());

            return cartItemDTO;
        } else {
            // Якщо продукт не знайдений в корзині, тоді додаємо новий запис
            CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
            cartItem.setId(cartItemDTO.getProductId());
            cartItem.setQuantity(cartItem.getQuantity());
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setModifiedAt(LocalDateTime.now());
            cartItem.setProduct(product);
            cartItem.setShoppingSession(shoppingSession);

            CartItem newCartItem = cartItemRepository.save(cartItem);

            // Оновити кількість у CartItemDTO перед поверненням
            cartItemDTO.setQuantity(newCartItem.getQuantity());

            return cartItemMapper.toDTO(newCartItem);
        }
    }


 /*  @Transactional
    public CartItemDTO addToCart(Long shoppingId, CartItemDTO cartItemDTO) {
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + cartItemDTO.getProductId() + " not found"));

        ShoppingSession shoppingSession = shoppingSessionRepository.findById(shoppingId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping Session with id " + shoppingId + " not found"));

        // Перевіряємо наявність продукту в корзині за допомогою product.getId() і shoppingSession.getId()
        CartItem existingCartItem = cartItemRepository.findByProductAndShoppingSession(product, shoppingSession);

        if (existingCartItem != null) {
            // Якщо продукт вже є в корзині, виводимо повідомлення про це
            throw new DataIntegrityViolationException("Продукт уже знаходиться в корзині.");
        } else {

            // Якщо продукт не знайдений в корзині, тоді додаємо його
            CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
            cartItem.setId(cartItemDTO.getProductId());
            cartItem.setQuantity(1L);
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setModifiedAt(LocalDateTime.now());
            cartItem.setProduct(product);
            cartItem.setShoppingSession(shoppingSession);

            CartItem newCartItem = cartItemRepository.save(cartItem);

            return cartItemMapper.toDTO(newCartItem);
        }
    }*/

    @Transactional
    public List<ProductDTO> getProductsInCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingSessionId(cartId);
        List<ProductDTO> products = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductDTO productDTO = productMapper.toDTO(cartItem.getProduct());
            productDTO.setQuantity(cartItem.getQuantity()); // Додати кількість до об'єкту ProductDTO
            products.add(productDTO);
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