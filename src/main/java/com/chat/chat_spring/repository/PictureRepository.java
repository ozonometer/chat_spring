package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PictureRepository extends MongoRepository<Picture, String> {

    Picture getByUserId(Integer userId);
}
