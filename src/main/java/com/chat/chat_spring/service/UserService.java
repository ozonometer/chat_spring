package com.chat.chat_spring.service;

import com.chat.chat_spring.model.UserModel;
import com.chat.chat_spring.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 2.1.1 Spring Boot, communicate with MongoDB
 * User service implementation
 */
@Service
public class UserService implements UserDetailsService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets all users, not in user and can be deleted
     * @return list of all users
     */
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Saves new or updated existing user
     * @param userModel UserModel object
     * @return
     */
    public UserModel saveOrUpdate(UserModel userModel) {
        return userRepository.save(userModel);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Gets existing user by id
     * @param id of the user
     * @return UserModel object
     */
    public UserModel getUserUserById(Integer id) {
        return userRepository.findUserByUserId(id);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Find user by name
     * @param name user name
     * @return UserModel object
     */
    public UserModel findUserByName(String name) {
        return userRepository.findUserByUserName(name);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Find user by username, used to prevent saving user with the same username
     * @param username to search for
     * @return UserModel object if match is found
     */
    public UserModel findByUserName(String username) {
        return userRepository.findFirstByUserName(username);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Updates existing user info
     * @param user UserModel object
     * @return updated UserModel
     */
    public UserModel updateUser(UserModel user) {
        return userRepository.save(user);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Find user with maximum user id, used to increment user id when saving new user
     * @return new user UserModel object
     */
    public UserModel findUserMaxId() {
        return userRepository.findFirstByOrderByUserIdDesc();
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Load user  by username, used for authentication
     * @param username to load
     * @return UserDetails object
     * @throws UsernameNotFoundException exception if not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String name;
        String pass;
        UserModel userModel = userRepository.findUserByUserName(username);
        if (userModel == null) {
            return null;
        } else {
            name = userModel.getUserName();
            pass = userModel.getPassword();
        }
        return new User(name, pass, new ArrayList<>());
    }
}
