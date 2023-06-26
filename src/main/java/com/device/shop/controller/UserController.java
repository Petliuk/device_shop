package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.UserDTO;
import com.device.shop.service.impl.UserImpl;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserImpl userImpl;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws BadRequestException {
        UserDTO savedUserDTO = userImpl.createUser(userDTO);
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId) {
        UserDTO userDTO = userImpl.getUserById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userImpl.getAllUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long userId, @RequestBody UserDTO userDTO) throws BadRequestException {
        UserDTO updatedUserDTO = userImpl.updateUser(userDTO, userId);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userImpl.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

}