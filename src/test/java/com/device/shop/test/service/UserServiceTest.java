package com.device.shop.test.service;

import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.UserMapper;
import com.device.shop.model.UserDTO;
import com.device.shop.repository.UserRepository;
import com.device.shop.service.impl.UserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

/*
    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserImpl(userRepository, userMapper);
    }

    @Test
    public void testCreateUser_ValidUser_Success() throws BadRequestException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("+380 99 123 45 67");

        UserDTO createdUser = userService.createUser(userDTO);
        Assertions.assertEquals(userDTO, createdUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_InvalidEmailFormat_ThrowsBadRequestException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("invalid-email");

        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(userDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUser_InvalidPhoneFormat_ThrowsBadRequestException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("12345");

        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(userDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetUserById_ExistingId_ReturnsUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPhone("+380 99 123 45 67");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO retrievedUser = userService.getUserById(1L);
        UserDTO expectedUser = new UserDTO();
        expectedUser.setId(1L);
        expectedUser.setEmail("test@example.com");
        expectedUser.setPhone("+380 99 123 45 67");
        Assertions.assertEquals(expectedUser, retrievedUser);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_NonExistingId_ThrowsEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllUsers_ReturnsListOfUsers() {
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

        List<UserDTO> retrievedList = userService.getAllUsers();
        List<UserDTO> expectedList = userList.stream().map(UserMapper::toDTO).collect(Collectors.toList());

        Assertions.assertEquals(expectedList, retrievedList);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_ValidUser_ReturnsUpdatedUser() throws BadRequestException, EntityNotFoundException {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("+380 99 123 45 67");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("existing@example.com");
        existingUser.setPhone("+380 99 999 99 99");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO updatedUser = userService.updateUser(userDTO, userId);
        UserDTO expectedUser = UserMapper.toDTO(existingUser);
        expectedUser.setEmail(userDTO.getEmail());
        expectedUser.setPhone(userDTO.getPhone());

        Assertions.assertEquals(expectedUser, updatedUser);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_NullUserId_ThrowsEntityNotFoundException() throws BadRequestException {
        Long userId = null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("+380 99 123 45 67");

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userDTO, userId));

        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_NonExistingUserId_ThrowsEntityNotFoundException() throws BadRequestException {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("+380 99 123 45 67");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userDTO, userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_ChangedId_ThrowsBadRequestException() throws BadRequestException {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("+380 99 123 45 67");

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));

        Assertions.assertThrows(BadRequestException.class, () -> userService.updateUser(userDTO, userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
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
*/

}