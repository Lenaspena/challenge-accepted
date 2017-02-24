package com.challengeaccepted.services;

import com.challengeaccepted.models.User;
import com.challengeaccepted.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUserToDatabase(User user) {
        userRepository.saveAndFlush(user);
    }

    public User getUserFromDatabase(Long id) {
        return userRepository.findOne(id);
    }

    public void updateUserInDatabase(User userFromWeb) {
        userRepository.save(userFromWeb);
    }

    public ArrayList<User> getAllUsersFromDatabase() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User getUserByEmailFromDatabase(String email) {
        return userRepository.findByEmail(email);
    }

}