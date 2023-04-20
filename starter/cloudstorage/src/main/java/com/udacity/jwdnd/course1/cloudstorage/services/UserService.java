package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    // variable for HashService
    private HashService hashService;
    // variable for UserMapper
    private UserMapper userMapper;

    // constructor with parameters for HashService and UserMapper
    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    // method to check if username is available
    public boolean checkUsername(String username) {
        // return userMapper.getUser(username) == null;
        return userMapper.getUser(username) == null;
    }

    // create user
    public int createUser(User newUser){
        SecureRandom random = new SecureRandom();
        // generate a random salt
        byte[] salt = new byte[16];
        // assign random value to salt
        random.nextBytes(salt);
        // encode the salt to a string
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        // get the hash value of the password using the salt
        String passwordSaltHash =  hashService.getHashedValue(newUser.getPassword(), encodedSalt);
        // create the user using the userMapper
        return userMapper.insert(new User(null, newUser.getUsername(), encodedSalt, passwordSaltHash, newUser.getFirstname(), newUser.getLastname()));
    }


}
