package com.chat.chat_spring.service;

import com.chat.chat_spring.model.Picture;
import com.chat.chat_spring.repository.PictureRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureServiceInt implements PictureService {
    @Autowired
    private PictureRepository pictureRepository;

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

    @Override
    public Picture getPictureByUserId(Integer userId) {
        return pictureRepository.getByUserId(userId);
    }

    @Override
    public void deletePicture(String id) {
        pictureRepository.deleteById(id);
    }
}
