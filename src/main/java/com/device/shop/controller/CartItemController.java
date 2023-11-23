package com.device.shop.controller;

import com.device.shop.model.CartItemDTO;
import com.device.shop.model.ProductDTO;
import com.device.shop.service.CartItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/cart/{id}/items")
    public ResponseEntity<CartItemDTO> addProduct(@PathVariable("id") Long id, @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO newCartItemDTO = cartItemService.addToCart(id, cartItemDTO);
        return new ResponseEntity<>(newCartItemDTO, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/cart/{id}/items")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable("id") Long cartId) {
        List<ProductDTO> products = cartItemService.getProductsInCart(cartId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<String> deleteCartItemById(@PathVariable("id") Long cartItemId) {
        cartItemService.deleteTheCartItem(cartItemId);
        return new ResponseEntity<>("Product successfully deleted from the cart", HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/cart/{cartId}/items")
    public ResponseEntity<String> deleteAllProducts(@PathVariable("cartId") Long cartId) {  //SessionId
        cartItemService.removeAllProductsFromCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body("All products removed from the cart");
    }

}
