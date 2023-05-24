package com.example.myprojectwithspring2.ClientUser.service;
import com.example.myprojectwithspring2.ClientUser.entity_class.BadRequestException;
import com.example.myprojectwithspring2.ClientUser.entity_class.User;

import java.util.List;

public interface UserServiseInterface {

    User createUser(User user);
    User getUserById(Long userId );
    List<User> getAllUser();
    User updateUser(User user, Long userId) throws BadRequestException;
    void deleteUser(Long userId);

}
