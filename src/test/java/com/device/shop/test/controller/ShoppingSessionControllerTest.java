package com.device.shop.test.controller;

import com.device.shop.controller.ShoppingSessionController;
import com.device.shop.entity.ShoppingSession;
import com.device.shop.service.ShoppingSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShoppingSessionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShoppingSessionService shoppingSessionService;

    @InjectMocks
    private ShoppingSessionController shoppingSessionController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingSessionController).build();
    }

    @Test
    public void testCreateShoppingSession() throws Exception {
        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setId(1L);
        shoppingSession.setTotal(100.0);

        when(shoppingSessionService.createShoppingSession(any(ShoppingSession.class))).thenReturn(shoppingSession);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(100.0));

        verify(shoppingSessionService, times(1)).createShoppingSession(any(ShoppingSession.class));
    }

    @Test
    public void testGetShoppingSessionById() throws Exception {
        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setId(1L);
        shoppingSession.setTotal(100.0);

        when(shoppingSessionService.getShoppingSessionById(1L)).thenReturn(shoppingSession);

        mockMvc.perform(get("/{sessionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(100.0));

        verify(shoppingSessionService, times(1)).getShoppingSessionById(1L);
    }

    @Test
    public void testGetAllShoppingSessions() throws Exception {
        ShoppingSession session1 = new ShoppingSession();
        session1.setId(1L);
        session1.setTotal(100.0);

        ShoppingSession session2 = new ShoppingSession();
        session2.setId(2L);
        session2.setTotal(200.0);

        List<ShoppingSession> shoppingSessions = Arrays.asList(session1, session2);

        when(shoppingSessionService.getAllShoppingSessions()).thenReturn(shoppingSessions);

        mockMvc.perform(get("/allSession"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].total").value(100.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].total").value(200.0));

        verify(shoppingSessionService, times(1)).getAllShoppingSessions();
    }

    @Test
    public void testUpdateShoppingSession() throws Exception {
        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setId(1L);
        shoppingSession.setTotal(100.0);

        when(shoppingSessionService.updateShoppingSession(any(ShoppingSession.class))).thenReturn(shoppingSession);

        mockMvc.perform(put("/{sessionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(100.0));

        verify(shoppingSessionService, times(1)).updateShoppingSession(any(ShoppingSession.class));
    }

    @Test
    public void testDeleteShoppingSessionById() throws Exception {
        mockMvc.perform(delete("/{sessionId}", 1L))
                .andExpect(status().isNoContent());

        verify(shoppingSessionService, times(1)).deleteShoppingSessionById(1L);
    }

}