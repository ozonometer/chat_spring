package com.chat.chat_spring.service;

import com.chat.chat_spring.model.Picture;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    String addPicture(String title, MultipartFile file, Integer userId);
    Picture getPictureByUserId(Integer userId);
    void deletePicture(String id);
}
