package com.device.shop.test.service;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.mapper.ShoppingSessionMapper;
import com.device.shop.model.ShoppingSessionDTO;
import com.device.shop.repository.ShoppingSessionRepository;
import com.device.shop.service.impl.ShoppingSessionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ShoppingSessionServiceTest {

    @Mock
    private ShoppingSessionRepository shoppingSessionRepository;

    @Mock
    private ShoppingSessionMapper shoppingSessionMapper;

    @InjectMocks
    private ShoppingSessionImpl shoppingSessionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateShoppingSession() {
        ShoppingSession shoppingSession = ShoppingSession.builder().id(1L).total(100.0).build();

        when(shoppingSessionRepository.save(Mockito.any(ShoppingSession.class))).thenReturn(shoppingSession);

        ShoppingSessionDTO expectedSession = shoppingSessionMapper.toDTO(shoppingSession);

        ShoppingSessionDTO createdSession = shoppingSessionService.createShoppingSession(new ShoppingSessionDTO());

        assertEquals(expectedSession, createdSession);
        verify(shoppingSessionRepository, times(1)).save(Mockito.any(ShoppingSession.class));
    }

    @Test
    public void testGetShoppingSessionById() {
        Long sessionId = 1L;
        ShoppingSession shoppingSession = ShoppingSession.builder().id(sessionId).total(100.0).build();

        when(shoppingSessionRepository.findById(sessionId)).thenReturn(Optional.of(shoppingSession));

        ShoppingSessionDTO expectedSession = shoppingSessionMapper.toDTO(shoppingSession);

        ShoppingSessionDTO retrievedSession = shoppingSessionService.getShoppingSessionById(sessionId);

        assertEquals(expectedSession, retrievedSession);
        verify(shoppingSessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testGetShoppingSessionById_ThrowsEntityNotFoundException() {
        Long sessionId = 1L;

        when(shoppingSessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> shoppingSessionService.getShoppingSessionById(sessionId));
        verify(shoppingSessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testGetAllShoppingSessions() {
        ShoppingSession session1 = ShoppingSession.builder().id(1L).total(100.0).build();
        ShoppingSession session2 = ShoppingSession.builder().id(2L).total(200.0).build();
        List<ShoppingSession> sessions = Arrays.asList(session1, session2);

        when(shoppingSessionRepository.findAll()).thenReturn(sessions);

        List<ShoppingSessionDTO> expectedSessions = sessions.stream().map(shoppingSessionMapper::toDTO).collect(Collectors.toList());

        List<ShoppingSessionDTO> retrievedSessions = shoppingSessionService.getAllShoppingSessions();

        assertEquals(expectedSessions, retrievedSessions);
        verify(shoppingSessionRepository, times(1)).findAll();
    }

}