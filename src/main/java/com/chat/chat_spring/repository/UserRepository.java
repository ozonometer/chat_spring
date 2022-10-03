package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, String> {

    List<UserModel> findAll();
    UserModel findUserByUserName(String name);
    UserModel findUserByUserId(@NotNull(message = "Id cannot be empty") Integer userId);
    UserModel findFirstByUserName(String username);
    UserModel findFirstByOrderByUserIdDesc();
}
