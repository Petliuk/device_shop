package com.device.shop.service;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.repository.ShoppingSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingSessionService implements ShoppingSessionServiceInterface {

    ShoppingSessionRepository shoppingSessionRepository;

    @Transactional
    public ShoppingSession createShoppingSession(ShoppingSession shoppingSession) {
        return shoppingSessionRepository.save(shoppingSession);
    }

    @Transactional
    public ShoppingSession getShoppingSessionById(Long sessionId) {
        return shoppingSessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Shopping session not found"));
    }

    @Transactional
    public List<ShoppingSession> getAllShoppingSessions() {
        return shoppingSessionRepository.findAll();
    }

    public ShoppingSession updateShoppingSession(ShoppingSession updatedSession) {
        return shoppingSessionRepository.save(updatedSession);
    }

    @Transactional
    public void deleteShoppingSessionById(Long sessionId) {
        shoppingSessionRepository.deleteById(sessionId);
    }

}