package com.device.shop.controller;

import com.device.shop.entity.CartItem;
import com.device.shop.entity.Product;
import com.device.shop.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartItemController {

    CartItemService cartItemService;

    @PostMapping("/cart/{id}/items")
    public ResponseEntity<CartItem> addingProductToTheCartById(@PathVariable("id") Long productId) {
        CartItem cartItem = cartItemService.addToCart(productId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @GetMapping("/cart/{id}/products")
    public ResponseEntity<List<Product>> getProductsInCart(@PathVariable("id") Long cartId) {
        List<Product> products = cartItemService.getProductsInCart(cartId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/card/items/{id}")
    public ResponseEntity<Product> deleteTheProductsById (@PathVariable("id") Long productId){
        cartItemService.deleteTheProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/all/products/{cartId}")
    public ResponseEntity<String> deleteAllProducts (@PathVariable("cartId") Long cartId){
        cartItemService.removeAllProductsFromCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body("All products removed from the cart");
    }

}