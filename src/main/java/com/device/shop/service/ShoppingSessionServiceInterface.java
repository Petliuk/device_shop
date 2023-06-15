package com.device.shop.service;

import com.device.shop.entity.ShoppingSession;

import java.util.List;

public interface ShoppingSessionServiceInterface {

    ShoppingSession createShoppingSession(ShoppingSession shoppingSession);
    ShoppingSession getShoppingSessionById(Long sessionId);
    List<ShoppingSession> getAllShoppingSessions();
    void deleteShoppingSessionById(Long sessionId);
    ShoppingSession updateShoppingSession(ShoppingSession updatedSession);

}