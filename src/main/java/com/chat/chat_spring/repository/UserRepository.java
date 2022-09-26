package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, Integer> {

    List<UserModel> findAll();
    UserModel findUserByUserName(String name);
    UserModel findUserByUserId(Integer id);
    UserModel findFirstByUserName(String username);
}
