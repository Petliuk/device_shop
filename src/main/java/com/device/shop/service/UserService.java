package com.device.shop.service;

import com.device.shop.exception.BadRequestException;
import com.device.shop.model.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO) throws BadRequestException;

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO, Long userId) throws BadRequestException;

    void deleteUser(Long userId);

}