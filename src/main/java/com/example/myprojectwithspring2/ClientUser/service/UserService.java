package com.example.myprojectwithspring2.ClientUser.service;

import com.example.myprojectwithspring2.ClientUser.entity_class.BadRequestException;
import com.example.myprojectwithspring2.ClientUser.entity_class.User;
import com.example.myprojectwithspring2.ClientUser.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements UserServiseInterface {

    private final UserRepository userRepository;

 @Transactional
    public User createUser(User user) {
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

}


