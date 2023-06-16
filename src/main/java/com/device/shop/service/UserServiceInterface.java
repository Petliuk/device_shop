package com.device.shop.service;

import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;

import java.util.List;

public interface UserServiceInterface {

    User createUser(User user) throws BadRequestException;

    User getUserById(Long userId);

    List<User> getAllUser();

    User updateUser(User user, Long userId) throws BadRequestException;

    void deleteUser(Long userId);

}