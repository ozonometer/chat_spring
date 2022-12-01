package com.chat.chat_spring.service;

import com.chat.chat_spring.model.Picture;
import com.chat.chat_spring.repository.PictureRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PictureServiceIntTest {
    @Mock
    private PictureRepository pictureRepositoryTest;
    @InjectMocks
    private PictureServiceInt pictureServiceIntTest;

    /**
     * Test case 26
     * Requirement 1.4.1 and 2.1.1
     * @throws Exception
     */
    @Test
    void shouldAddPicture() {
        // arrange
        Picture pic = new Picture(null, 1, "test picture", new Binary(new byte[123456]));

        // act
        when(pictureRepositoryTest.insert(pic)).thenReturn(pic);
        String result = pictureServiceIntTest.addPicture("test picture", new MockMultipartFile("test picture", new Binary(new byte[123456]).getData()), 1);

        // assert
        verify(pictureRepositoryTest).insert(pic);
        assertEquals(pic.getId(), result);
    }

    /**
     * Test case 27
     * Requirement 1.2.5 and 2.1.1
     * @throws Exception
     */
    @Test
    void shouldGetPictureByUserId() {
        // arrange
        Picture pic = new Picture("001", 1, "test picture", new Binary(new byte[123456]));

        // act
        when(pictureRepositoryTest.getByUserId(any())).thenReturn(pic);
        Picture result = pictureServiceIntTest.getPictureByUserId(1);

        // assert
        assertEquals(pic, result);
    }

    /**
     * Test case 28
     * Requirement 1.4.2 and 2.1.1
     * @throws Exception
     */
    @Test
    void shouldDeletePicture() {
        // arrange
        Picture pic = new Picture("001", 1, "test picture", new Binary(new byte[123456]));

        // act
        pictureServiceIntTest.deletePicture(pic.getId());

        // assert
        verify(pictureRepositoryTest).deleteById(pic.getId());
    }
}