package com.device.shop.test.service;

import com.device.shop.entity.ShoppingSession;
import com.device.shop.repository.ShoppingSessionRepository;
import com.device.shop.service.ShoppingSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ShoppingSessionServiceTest {

    @Mock
    private ShoppingSessionRepository shoppingSessionRepository;

    @InjectMocks
    private ShoppingSessionService shoppingSessionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateShoppingSession() {
        ShoppingSession shoppingSession = ShoppingSession.builder().id(1L).total(100.0).build();

        when(shoppingSessionRepository.save(shoppingSession)).thenReturn(shoppingSession);

        ShoppingSession createdSession = shoppingSessionService.createShoppingSession(shoppingSession);

        assertEquals(shoppingSession, createdSession);
        verify(shoppingSessionRepository, times(1)).save(shoppingSession);
    }

    @Test
    public void testGetShoppingSessionById() {
        ShoppingSession shoppingSession = ShoppingSession.builder().id(1L).total(100.0).build();

        when(shoppingSessionRepository.findById(1L)).thenReturn(Optional.of(shoppingSession));

        ShoppingSession retrievedSession = shoppingSessionService.getShoppingSessionById(1L);

        assertEquals(shoppingSession, retrievedSession);
        verify(shoppingSessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetShoppingSessionById_ThrowsEntityNotFoundException() {

        when(shoppingSessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> shoppingSessionService.getShoppingSessionById(1L));
        verify(shoppingSessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllShoppingSessions() {
        ShoppingSession session1 = ShoppingSession.builder().id(1L).total(100.0).build();
        ShoppingSession session2 = ShoppingSession.builder().id(2L).total(200.0).build();
        List<ShoppingSession> sessions = Arrays.asList(session1, session2);

        when(shoppingSessionRepository.findAll()).thenReturn(sessions);

        List<ShoppingSession> retrievedSessions = shoppingSessionService.getAllShoppingSessions();

        assertEquals(sessions, retrievedSessions);
        verify(shoppingSessionRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteShoppingSessionById() {
        Long sessionId = 1L;

        shoppingSessionService.deleteShoppingSessionById(sessionId);

        verify(shoppingSessionRepository, times(1)).deleteById(sessionId);
    }

}