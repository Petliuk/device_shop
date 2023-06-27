package com.device.shop.service.impl;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.entity.User;
import com.device.shop.mapper.ShoppingSessionMapper;
import com.device.shop.model.ShoppingSessionDTO;
import com.device.shop.repository.ShoppingSessionRepository;
import com.device.shop.repository.UserRepository;
import com.device.shop.service.ShoppingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShoppingSessionServiceImpl implements ShoppingSessionService {
    //toDo decouple
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final ShoppingSessionMapper shoppingSessionMapper;
    private final UserRepository userRepository;

    @Transactional
    public ShoppingSessionDTO createShoppingSession(ShoppingSessionDTO shoppingSessionDTO) {
        ShoppingSession shoppingSession = shoppingSessionMapper.toEntity(shoppingSessionDTO);

        User user = userRepository.findById(shoppingSessionDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + shoppingSessionDTO.getUserId() + " not found"));
        shoppingSession.setUser(user);

        ShoppingSession createdSession = shoppingSessionRepository.save(shoppingSession);
        return shoppingSessionMapper.toDTO(createdSession);
    }

    @Transactional
    public ShoppingSessionDTO getShoppingSessionById(Long sessionId) {
        ShoppingSession shoppingSession = shoppingSessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Shopping session not found"));
        return shoppingSessionMapper.toDTO(shoppingSession);
    }

    @Transactional
    public List<ShoppingSessionDTO> getAllShoppingSessions() {
        List<ShoppingSession> shoppingSessions = shoppingSessionRepository.findAll();
        return shoppingSessions.stream().map(shoppingSessionMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ShoppingSessionDTO updateShoppingSession(Long sessionId, ShoppingSessionDTO updatedSessionDTO) {
        ShoppingSession existingSession = shoppingSessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Shopping session not found"));

        existingSession.setTotal(updatedSessionDTO.getTotal());
        existingSession.setCreatedAt(LocalDateTime.now());
        existingSession.setModifiedAt(LocalDateTime.now());

        ShoppingSession updatedSession = shoppingSessionRepository.save(existingSession);
        return shoppingSessionMapper.toDTO(updatedSession);
    }

    @Transactional
    public ResponseEntity<String> deleteShoppingSessionById(Long sessionId) {
        ShoppingSession existingSession = shoppingSessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Shopping session not found"));

        shoppingSessionRepository.delete(existingSession);

        return ResponseEntity.ok("Shopping session deleted successfully.");
    }

}