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

@Service
public class UserService implements UserDetailsService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel saveOrUpdate(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public UserModel getUserUserById(Integer id) {
        return userRepository.findUserByUserId(id);
    }

    public UserModel findUserByName(String name) {
        return userRepository.findUserByUserName(name);
    }

    public UserModel findByUserName(String username) {
        return userRepository.findFirstByUserName(username);
    }

    public UserModel updateUser(UserModel user) {
        return userRepository.save(user);
    }

    public UserModel findUserMaxId() {
        return userRepository.findFirstByOrderByUserIdDesc();
    }

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
