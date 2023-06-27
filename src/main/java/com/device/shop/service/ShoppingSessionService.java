package com.device.shop.service;

import com.device.shop.model.ShoppingSessionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShoppingSessionService {

    ShoppingSessionDTO createShoppingSession(ShoppingSessionDTO shoppingSessionDTO);

    ShoppingSessionDTO getShoppingSessionById(Long sessionId);

    List<ShoppingSessionDTO> getAllShoppingSessions();

    ShoppingSessionDTO updateShoppingSession(Long sessionId, ShoppingSessionDTO updatedSessionDTO);

    ResponseEntity<String> deleteShoppingSessionById(Long sessionId);

}