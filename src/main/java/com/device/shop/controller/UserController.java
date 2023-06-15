package com.device.shop.controller;

import com.device.shop.exception.BadRequestException;
import com.device.shop.entity.User;
import com.device.shop.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws BadRequestException {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody User user) throws BadRequestException {
        User updatedUser = userService.updateUser(user, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

}