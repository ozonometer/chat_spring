package com.chat.chat_spring.service;

import com.chat.chat_spring.model.Picture;
import com.chat.chat_spring.repository.PictureRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Requirement 2.1.1 Spring Boot, communicate with MongoDB
 * Picture service implementation
 */
@Service
public class PictureServiceInt implements PictureService {
    @Autowired
    private PictureRepository pictureRepository;

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Saves user picture to the database
     * @param title picture title
     * @param file picture file
     * @param userId user id
     * @return saved picture id
     */
    @Override
    public String addPicture(String title, MultipartFile file, Integer userId) {
        Picture pic = new Picture();
        pic.setTitle(title);
        pic.setUserId(userId);
        try {
            pic.setImage(
                    new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pic = pictureRepository.insert(pic);
        return pic.getId();
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Gets user picture
     * @param userId user id
     * @return Picture object
     */
    @Override
    public Picture getPictureByUserId(Integer userId) {
        return pictureRepository.getByUserId(userId);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Deletes picture by user id
     * @param id of the user
     */
    @Override
    public void deletePicture(String id) {
        pictureRepository.deleteById(id);
    }
}
