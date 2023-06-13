package com.device.shop.test.controller;

import com.device.shop.controller.UserController;
import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;
import com.device.shop.exception.ExceptionController;
import com.device.shop.service.UserService;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    private UserController userController;

    @BeforeEach
    public void setup() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setId(10L);
        user.setName("Alesia");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Alesia"));
    }

    @Test
    public void testCreateUser_DataIntegrityViolationException() throws Exception {
        User user = User.builder()
                .name("Alesia")
                .surname("Pav")
                .phone("0977364523")
                .email("john.doe09@gmail.com")
                .password("password")
                .build();

        when(userService.createUser(any(User.class))).thenThrow(new DataIntegrityViolationException(""));

        mockMvc.perform(post("/users")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUser_BadRequestException() throws Exception {
        User user = User.builder()
                .name("Alesia")
                .surname("Pav")
                .phone("0977364523")
                .email("")
                .password("password")
                .build();
        when(userService.createUser(any(User.class))).thenThrow(new BadRequestException(""));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
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
        doThrow(new EntityNotFoundException()).when(userService).getUserById(anyLong());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUser() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");

        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUser()).thenReturn(users);

        mockMvc.perform(get("/allUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(10L);
        user.setName("Alesia");

        when(userService.updateUser(any(User.class), any(Long.class))).thenReturn(user);

        mockMvc.perform(
                post("/{id}", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Alesia"));
    }

    @Test
    public void testUpdateUser_EntityNotFoundException() throws Exception {
        User user = User.builder()
                .name("Alesia")
                .surname("Pav")
                .phone("0977364523")
                .email("john.doe09@gmail.com")
                .password("password")
                .build();

        doThrow(new EntityNotFoundException()).when(userService).updateUser(any(User.class), anyLong());

        mockMvc.perform(post("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))).
                andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser_BadRequestException() throws Exception {
        User user = User.builder()
                .name("Alesia")
                .surname("Pav")
                .phone("0977364523")
                .email("john.doe09@gmail.com")
                .password("password")
                .build();
        doThrow(new BadRequestException("")).when(userService).updateUser(any(User.class), anyLong());

        mockMvc.perform(post("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUser_DataIntegrityViolationException() throws Exception {
        User user = User.builder()
                .name("Alesia")
                .surname("Pav")
                .phone("0977364523")
                .email("john.doe09@gmail.com")
                .password("password")
                .build();
        doThrow(new DataIntegrityViolationException("")).when(userService).updateUser(any(User.class), anyLong());

        mockMvc.perform(post("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully deleted!"));
    }

    @Test
    public void testDeleteUser_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}

