package com.challengeaccepted.services;

import com.challengeaccepted.loggers.HerokuLogger;
import com.challengeaccepted.loggers.HerokuLoggerException;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUserToDatabase(UserModel userModel) {
        userRepository.saveAndFlush(userModel);
    }

    public UserModel getUserFromDatabase(Long id) {
        return userRepository.findOne(id);
    }

    public void updateUserInDatabase(UserModel userModelFromWeb) {
        userRepository.save(userModelFromWeb);
    }

    public ArrayList<UserModel> getAllUsersFromDatabase() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel getUserByEmailFromDatabase(String email) throws HerokuLoggerException {
        new HerokuLogger().writeToInfoLog("Total number of users: " + userRepository.findAll().size());
        return userRepository.findByEmail(email);
    }

}