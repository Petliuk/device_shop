package com.device.shop.service;

import com.device.shop.entity.User;
import com.device.shop.repository.UserRepository;
import com.device.shop.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) throws BadRequestException {
        validateUserFields(user);
        return userRepository.save(user);
    }

    @Transactional
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }

    @Transactional
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user, Long userId) throws BadRequestException, EntityNotFoundException {
        if (userId == null || !userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        } else if (!userId.equals(user.getId())) {
            throw new BadRequestException("Cannot change the id to " + user.getId());
        } else {
            validateUserFields(user);
            return userRepository.save(user);
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
    }

    private void validateUserFields(User user) throws BadRequestException {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(user.getEmail());
        if (!emailMatcher.matches()) {
            throw new BadRequestException("Invalid email format");
        }
        String phoneRegex = "^\\+380\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}$|^\\+380-\\d{2}-\\d{3}-\\d{2}-\\d{2}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(user.getPhone());
        if (!phoneMatcher.matches()) {
            throw new BadRequestException("Invalid phone number format");
        }
    }

}
