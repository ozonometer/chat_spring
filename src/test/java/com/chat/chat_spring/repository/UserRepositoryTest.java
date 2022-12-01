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

    /**
     * Test case 14
     * Requirement 1.1.2, 3.1.1, and 3.1.2
     * @throws Exception
     */
    @Test
    void shouldFindAll() {
        // arrange
        userRepoTest.deleteAll();
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

    /**
     * Test case 15
     * Requirement 1.1.1, 3.1.1, and 3.1.2
     * @throws Exception
     */
    @Test
    void shouldFindUserByUserName() {
        // arrange
        userRepoTest.deleteAll();
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");
        UserModel user2 = new UserModel("002", 2, "test2", "user2", "test_user2",
                "test_password2","test_city2", "test_state2", "test_zipcode2", "test_country2");
        UserModel user3 = new UserModel("003", 3, "test3", "user3", "test_user3",
                "test_password3","test_city3", "test_state3", "test_zipcode3", "test_country3");
        List<UserModel> userList = new LinkedList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userRepoTest.saveAll(userList);

        // act
        List<UserModel> result = userRepoTest.findAll();

        //assert
        assertEquals(userList, result);
    }

    /**
     * Test case 16
     * Requirement 1.1.2, 3.1.1, and 3.1.2
     * @throws Exception
     */@Test
    void shouldFindUserByUserId() {
        // arrange
        userRepoTest.deleteAll();
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");
        userRepoTest.save(user1);

        // act
        UserModel result = userRepoTest.findUserByUserId(1);

        // assert
        assertEquals(user1, result);
    }

    /**
     * Test case 17
     * Requirement 1.1.2, 3.1.1, and 3.1.2
     * @throws Exception
     */@Test
    void shouldFindFirstByUserName() {
        // arrange
        userRepoTest.deleteAll();
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");
        UserModel user2 = new UserModel("002", 2, "test2", "user2", "test_user",
                "test_password2","test_city2", "test_state2", "test_zipcode2", "test_country2");
        userRepoTest.save(user1);
        userRepoTest.save(user2);

        // act
        UserModel result = userRepoTest.findFirstByUserName("test_user");

        // assert
        assertEquals(user1, result);
        assertNotEquals(user2, result);
    }

    /**
     * Test case 18
     * Requirement 1.1.2, 3.1.1, and 3.1.2
     * @throws Exception
     */@Test
    void shouldFindFirstByOrderByUserIdDesc() {
        // arrange
        userRepoTest.deleteAll();
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");
        UserModel user2 = new UserModel("002", 2, "test2", "user2", "test_user2",
                "test_password2","test_city2", "test_state2", "test_zipcode2", "test_country2");
        UserModel user3 = new UserModel("003", 3, "test3", "user3", "test_user3",
                "test_password3","test_city3", "test_state3", "test_zipcode3", "test_country3");
        List<UserModel> userList = new LinkedList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userRepoTest.saveAll(userList);

        // act
        UserModel result = userRepoTest.findFirstByOrderByUserIdDesc();

        // assert
        assertEquals(user3, result);
    }
}
