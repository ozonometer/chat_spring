package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.Picture;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class PictureRepositoryTest {
    @Autowired
    private PictureRepository picRepoTest;

    @Test
    void shouldGetPictureByUserId() {
        // arrange
        picRepoTest.deleteAll();
        Picture pic = new Picture("001", 1, "test picture", new Binary(new byte[123456]));
        picRepoTest.save(pic);

        // act
        Picture result = picRepoTest.getByUserId(1);

        // assert
        assertEquals(pic, result);
    }
}