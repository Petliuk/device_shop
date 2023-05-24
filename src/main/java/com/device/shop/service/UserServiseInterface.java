package com.device.shop.service;
import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;

import java.util.List;

public interface UserServiseInterface {

    User createUser(User user);
    User getUserById(Long userId );
    List<User> getAllUser();
    User updateUser(User user, Long userId) throws BadRequestException;
    void deleteUser(Long userId);

}
