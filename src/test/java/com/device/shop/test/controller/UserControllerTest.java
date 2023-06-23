package com.device.shop.test.controller;

import com.device.shop.controller.UserController;
import com.device.shop.exception.BadRequestException;
import com.device.shop.exception.ExceptionController;
import com.device.shop.model.UserDTO;
import com.device.shop.service.impl.UserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserImpl userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    private UserController userController;

    @BeforeEach
    public void setup() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ExceptionController()).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(10L);
        user.setName("Alesia");

        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Alesia"));
    }

    @Test
    public void testCreateUser_DataIntegrityViolationException() throws Exception {
        UserDTO user = new UserDTO();
        user.setName("Alesia");
        user.setSurname("Pav");
        user.setPhone("0977364523");
        user.setEmail("john.doe09@gmail.com");
        user.setPassword("password");

        when(userService.createUser(any(UserDTO.class))).thenThrow(new DataIntegrityViolationException(""));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUser_BadRequestException() throws Exception {
        UserDTO user = new UserDTO();
        user.setName("Alesia");
        user.setSurname("Pav");
        user.setPhone("0977364523");
        user.setEmail("");
        user.setPassword("password");

        when(userService.createUser(any(UserDTO.class))).thenThrow(new BadRequestException(""));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(10L);
        user.setName("Alesia");

        when(userService.getUserById(10L)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Alesia"));
    }

    @Test
    public void testGetUserById_EntityNotFoundException() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully deleted!"));

        verify(userService, times(1)).deleteUser(10L);
    }

    @Test
    public void testDeleteUser_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException()).when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUser(1L);
    }

}

