package com.device.shop.mapper;

import com.device.shop.entity.Role;
import com.device.shop.entity.User;

import com.device.shop.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
