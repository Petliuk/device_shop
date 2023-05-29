package com.device.shop.test.service;

import com.device.shop.entity.User;
import com.device.shop.repository.UserRepository;
import com.device.shop.exception.BadRequestException;
import com.device.shop.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testCreateUser_ValidUser_Success() throws BadRequestException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        Assertions.assertEquals(user, createdUser);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreateUser_InvalidEmailFormat_ThrowsBadRequestException() {
        User user = new User();
        user.setEmail("invalid-email");

        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUser_InvalidPhoneFormat_ThrowsBadRequestException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhone("12345");

        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetUserById_ExistingId_ReturnsUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);
        Assertions.assertEquals(user, retrievedUser);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_NonExistingId_ThrowsEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllUser_ReturnsListOfUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@example.com");
        user1.setPhone("+380 99 123 45 67");
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");
        user2.setPhone("+380 99 987 65 43");
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> retrievedList = userService.getAllUser();
        Assertions.assertEquals(userList, retrievedList);

        verify(userRepository, times(1)).findAll();
    }
    @Test
    void updateUser_ValidUser_ReturnsUpdatedUser() throws BadRequestException, EntityNotFoundException {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user, userId);
        Assertions.assertEquals(user, updatedUser);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_NullUserId_ThrowsEntityNotFoundException() throws BadRequestException {
        Long userId = null;
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user, userId));

        verify(userRepository, never()).existsById(userId);
        verify(userRepository, never()).save(user);
    }
    @Test
    void updateUser_NonExistingUserId_ThrowsEntityNotFoundException() throws BadRequestException {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user, userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).save(user);
    }

    @Test
    void updateUser_ChangedId_ThrowsBadRequestException() throws BadRequestException {
        Long userId = 1L;
        User user = new User();
        user.setId(2L);
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.existsById(userId)).thenReturn(true);

        Assertions.assertThrows(BadRequestException.class, () -> userService.updateUser(user, userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).save(user);
    }

    @Test
    void deleteUser_ExistingUserId_DeletesUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_NonExistingUserId_ThrowsEntityNotFoundException() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

}


