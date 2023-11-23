package com.device.shop.controller;

import com.device.shop.model.ShoppingSessionDTO;
import com.device.shop.service.ShoppingSessionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ShoppingSessionController {

    private final ShoppingSessionService shoppingSessionService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create")
    public ResponseEntity<ShoppingSessionDTO> createShoppingSession(@RequestBody ShoppingSessionDTO shoppingSessionDTO) {
        ShoppingSessionDTO createdSession = shoppingSessionService.createShoppingSession(shoppingSessionDTO);
        return ResponseEntity.ok(createdSession);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{sessionId}")
    public ResponseEntity<ShoppingSessionDTO> getShoppingSessionById(@PathVariable Long sessionId) {
        ShoppingSessionDTO shoppingSession = shoppingSessionService.getShoppingSessionById(sessionId);
        return ResponseEntity.ok(shoppingSession);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/allSession")
    public ResponseEntity<List<ShoppingSessionDTO>> getAllShoppingSessions() {
        List<ShoppingSessionDTO> shoppingSessions = shoppingSessionService.getAllShoppingSessions();
        return ResponseEntity.ok(shoppingSessions);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{sessionId}")
    public ResponseEntity<ShoppingSessionDTO> updateShoppingSession(@PathVariable Long sessionId, @RequestBody ShoppingSessionDTO updatedSessionDTO) {
        ShoppingSessionDTO updated = shoppingSessionService.updateShoppingSession(sessionId, updatedSessionDTO);
        return ResponseEntity.ok(updated);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<String> deleteShoppingSessionById(@PathVariable Long sessionId) {
        shoppingSessionService.deleteShoppingSessionById(sessionId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
    }

}