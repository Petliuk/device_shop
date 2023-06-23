package com.device.shop.service.impl;

import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.UserMapper;
import com.device.shop.model.UserDTO;
import com.device.shop.repository.UserRepository;
import com.device.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws BadRequestException {
        validateUserFields(userDTO);
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        return userMapper.toDTO(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO, Long userId) throws BadRequestException {
        validateUserFields(userDTO);

        if (userId == null) {
            throw new EntityNotFoundException("User ID cannot be null");
        }

        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        if (!userId.equals(userDTO.getId())) {
            throw new BadRequestException("Cannot change the ID to " + userDTO.getId());
        }

        User updatedUser = userMapper.toEntity(userDTO);
        updatedUser.setId(existingUser.getId());
        User savedUser = userRepository.save(updatedUser);
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
    }

    private void validateUserFields(UserDTO userDTO) throws BadRequestException {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(userDTO.getEmail());
        if (!emailMatcher.matches()) {
            throw new BadRequestException("Invalid email format");
        }
        String phoneRegex = "^\\+380\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}$|^\\+380-\\d{2}-\\d{3}-\\d{2}-\\d{2}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(userDTO.getPhone());
        if (!phoneMatcher.matches()) {
            throw new BadRequestException("Invalid phone number format");
        }
    }

}