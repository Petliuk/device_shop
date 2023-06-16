package com.device.shop.controller;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.service.ShoppingSessionService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ShoppingSessionController {

    ShoppingSessionService shoppingSessionService;

    @PostMapping("/session")
    public ResponseEntity<ShoppingSession> createShoppingSession(@RequestBody ShoppingSession shoppingSession) {
        ShoppingSession createdSession = shoppingSessionService.createShoppingSession(shoppingSession);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<ShoppingSession> getShoppingSessionById(@PathVariable Long sessionId) {
        ShoppingSession shoppingSession = shoppingSessionService.getShoppingSessionById(sessionId);
        return ResponseEntity.ok(shoppingSession);
    }

    @GetMapping("/session")
    public ResponseEntity<List<ShoppingSession>> getAllShoppingSessions() {
        List<ShoppingSession> shoppingSessions = shoppingSessionService.getAllShoppingSessions();
        return ResponseEntity.ok(shoppingSessions);
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<ShoppingSession> updateShoppingSession(@PathVariable Long sessionId, @RequestBody ShoppingSession updatedSession) {
        updatedSession.setId(sessionId);
        ShoppingSession updated = shoppingSessionService.updateShoppingSession(updatedSession);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteShoppingSessionById(@PathVariable Long sessionId) {
        shoppingSessionService.deleteShoppingSessionById(sessionId);
        return ResponseEntity.noContent().build();
    }

}