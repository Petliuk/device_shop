package com.device.shop.controller;

import com.device.shop.model.ShoppingSessionDTO;
import com.device.shop.service.impl.ShoppingSessionImpl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ShoppingSessionController {

    private final ShoppingSessionImpl shoppingSessionImpl;

    @PostMapping("/create")
    public ResponseEntity<ShoppingSessionDTO> createShoppingSession(@RequestBody ShoppingSessionDTO shoppingSessionDTO) {
        ShoppingSessionDTO createdSession = shoppingSessionImpl.createShoppingSession(shoppingSessionDTO);
        return ResponseEntity.ok(createdSession);
    }


    @GetMapping("/{sessionId}")
    public ResponseEntity<ShoppingSessionDTO> getShoppingSessionById(@PathVariable Long sessionId) {
        ShoppingSessionDTO shoppingSession = shoppingSessionImpl.getShoppingSessionById(sessionId);
        return ResponseEntity.ok(shoppingSession);
    }


    @GetMapping("/allSession")
    public ResponseEntity<List<ShoppingSessionDTO>> getAllShoppingSessions() {
        List<ShoppingSessionDTO> shoppingSessions = shoppingSessionImpl.getAllShoppingSessions();
        return ResponseEntity.ok(shoppingSessions);
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<ShoppingSessionDTO> updateShoppingSession(@PathVariable Long sessionId, @RequestBody ShoppingSessionDTO updatedSessionDTO) {
        ShoppingSessionDTO updated = shoppingSessionImpl.updateShoppingSession(sessionId, updatedSessionDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<String> deleteShoppingSessionById(@PathVariable Long sessionId) {
        shoppingSessionImpl.deleteShoppingSessionById(sessionId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
    }

}