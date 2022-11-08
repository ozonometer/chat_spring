package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepoTest;

    @Test
    void shouldFindAll() {
        // arrange
        List<UserModel> userList = new LinkedList<>();
        userList.add(new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country"));
        userList.add(new UserModel("002", 2, "test2", "user2", "test_user2",
                "test_password2","test_city2", "test_state2", "test_zipcode2", "test_country2"));
        userList.add(new UserModel("003", 3, "test3", "user3", "test_user3",
                "test_password3","test_city3", "test_state3", "test_zipcode3", "test_country3"));
        userRepoTest.saveAll(userList);

        // act
        List<UserModel> result = userRepoTest.findAll();

        //assert
        assertEquals(userList, result);
    }

    @Test
    void shouldFindUserByUserName() {
    }

    @Test
    void shouldFindUserByUserId() {
    }

    @Test
    void shouldFindFirstByUserName() {
    }

    @Test
    void shouldFindFirstByOrderByUserIdDesc() {
    }
}