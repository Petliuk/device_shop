package com.device.shop.controller;

import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.service.impl.CartItemImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartItemController {

    CartItemImpl cartItemService;

    @PostMapping("/cart/{id}/items")
    public ResponseEntity<CartItemDTO> addProduct(@PathVariable("id") Long id, @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO newCartItemDTO = cartItemService.addToCart(id, cartItemDTO);
        return new ResponseEntity<>(newCartItemDTO, HttpStatus.OK);
    }

    @GetMapping("/cart/{id}/items")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable("id") Long cartId) {
        List<ProductDTO> products = cartItemService.getProductsInCart(cartId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long productId) {
        cartItemService.deleteTheProduct(productId);
        return new ResponseEntity<>("Product successfully deleted from the cart", HttpStatus.OK);
    }

    @DeleteMapping("/cart/{cartId}/items")
    public ResponseEntity<String> deleteAllProducts(@PathVariable("cartId") Long cartId) {  //SessionId
        cartItemService.removeAllProductsFromCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body("All products removed from the cart");
    }

}