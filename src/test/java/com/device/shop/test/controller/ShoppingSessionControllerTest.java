package com.device.shop.test.controller;

import com.device.shop.controller.ShoppingSessionController;
import com.device.shop.model.ShoppingSessionDTO;
import com.device.shop.service.impl.ShoppingSessionServiceImpl;
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
    private ShoppingSessionServiceImpl shoppingSessionImpl;

    @InjectMocks
    private ShoppingSessionController shoppingSessionController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingSessionController).build();
    }

    @Test
    public void testCreateShoppingSession() throws Exception {
        ShoppingSessionDTO shoppingSessionDTO = ShoppingSessionDTO.builder()
                .id(1L)
                .total(100.0)
                .build();

        when(shoppingSessionImpl.createShoppingSession(any(ShoppingSessionDTO.class))).thenReturn(shoppingSessionDTO);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(100.0));

        verify(shoppingSessionImpl, times(1)).createShoppingSession(any(ShoppingSessionDTO.class));
    }

    @Test
    public void testGetShoppingSessionById() throws Exception {
        ShoppingSessionDTO shoppingSessionDTO = ShoppingSessionDTO.builder()
                .id(1L)
                .total(100.0)
                .build();

        when(shoppingSessionImpl.getShoppingSessionById(1L)).thenReturn(shoppingSessionDTO);

        mockMvc.perform(get("/{sessionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(100.0));

        verify(shoppingSessionImpl, times(1)).getShoppingSessionById(1L);
    }

    @Test
    public void testGetAllShoppingSessions() throws Exception {
        ShoppingSessionDTO session1 = ShoppingSessionDTO.builder()
                .id(1L)
                .total(100.0)
                .build();

        ShoppingSessionDTO session2 = ShoppingSessionDTO.builder()
                .id(2L)
                .total(200.0)
                .build();

        List<ShoppingSessionDTO> shoppingSessions = Arrays.asList(session1, session2);

        when(shoppingSessionImpl.getAllShoppingSessions()).thenReturn(shoppingSessions);

        mockMvc.perform(get("/allSession"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].total").value(100.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].total").value(200.0));

        verify(shoppingSessionImpl, times(1)).getAllShoppingSessions();
    }

    @Test
    public void testUpdateShoppingSession() throws Exception {
        ShoppingSessionDTO updatedSessionDTO = new ShoppingSessionDTO();
        updatedSessionDTO.setTotal(100.0);

        ShoppingSessionDTO updatedSession = new ShoppingSessionDTO();
        updatedSession.setId(1L);
        updatedSession.setTotal(100.0);

        when(shoppingSessionImpl.updateShoppingSession(anyLong(), any(ShoppingSessionDTO.class))).thenReturn(updatedSession);

        mockMvc.perform(put("/{sessionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(100.0));

        verify(shoppingSessionImpl, times(1)).updateShoppingSession(anyLong(), any(ShoppingSessionDTO.class));
    }

    @Test
    public void testDeleteShoppingSessionById() throws Exception {
        mockMvc.perform(delete("/{sessionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Product successfully deleted!"));

        verify(shoppingSessionImpl, times(1)).deleteShoppingSessionById(1L);
    }

}