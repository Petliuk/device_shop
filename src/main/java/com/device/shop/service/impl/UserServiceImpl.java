package com.device.shop.service.impl;

import com.device.shop.model.ERole;
import com.device.shop.entity.Role;
import com.device.shop.entity.User;
import com.device.shop.exception.BadRequestException;
import com.device.shop.mapper.UserMapper;
import com.device.shop.model.UserDTO;
import com.device.shop.repository.RoleRepository;
import com.device.shop.repository.UserRepository;
import com.device.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws BadRequestException {
        validateUserFields(userDTO);
        User user = userMapper.toEntity(userDTO);
        setUserRoles(user, userDTO);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);
/*        //todo move send email via spring AOP and remove flush operation
        userRepository.flush();
        try {
            emailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getName());
        } catch (MessagingException e) {

        }*/

        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        return userMapper.toDTO(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Отримати ім'я користувача з контексту безпеки
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        return users.stream()
                .filter(user -> !user.getRoles().stream().anyMatch(role -> role.getName() == ERole.ROLE_ADMIN && user.getEmail().equals(username)))
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
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
        setUserRoles(updatedUser, userDTO);
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
        String phoneRegex = "^(\\+380\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}|\\+380-\\d{2}-\\d{3}-\\d{2}-\\d{2}|\\+380\\d{9})$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(userDTO.getPhone());
        if (!phoneMatcher.matches()) {
            throw new BadRequestException("Invalid phone number format");
        }
    }

     private void setUserRoles(User user, UserDTO userDTO){
         if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
             Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                     .orElseThrow(() -> new EntityNotFoundException("Default user role not found"));
             user.setRoles(Collections.singleton(userRole));
         } else {
             user.setRoles(userDTO.getRoles().stream()
                     .map(role -> roleRepository.findByName(role)
                             .orElseThrow(() -> new EntityNotFoundException("Default user role not found")))
                     .collect(Collectors.toSet()));
         }
     }

}