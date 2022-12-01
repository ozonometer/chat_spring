package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Requirement 2.1.1 Spring Boot, communicate with MongoDB
 * Picture Repository
 */
public interface PictureRepository extends MongoRepository<Picture, String> {

    Picture getByUserId(Integer userId);
}
