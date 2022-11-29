package com.chat.chat_spring.service;

import com.chat.chat_spring.model.UserModel;
import com.chat.chat_spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {
    @Mock
    private UserRepository userRepositoryTest;
    @InjectMocks
    private UserService userServiceTest;

    List<UserModel> userList;
    UserModel user1;
    UserModel user2;
    UserModel user3;

    @BeforeEach
    public void setUpDataBase(){
        user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");
        user2 = new UserModel("002", 2, "test2", "user2", "test_user2",
                "test_password2","test_city2", "test_state2", "test_zipcode2", "test_country2");
        user3 = new UserModel("003", 3, "test3", "user3", "test_user3",
                "test_password3","test_city3", "test_state3", "test_zipcode3", "test_country3");
        userList = new LinkedList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }

    @Test
    void shouldGetAllUsers() {
        // arrange

        // act
        when(userRepositoryTest.findAll()).thenReturn(userList);
        List<UserModel> resultList = userServiceTest.getAllUsers();

        // assert
        assertEquals(userList, resultList);
    }

    @Test
    void shouldSaveOrUpdate() {
        // arrange
        UserModel newUser = new UserModel("004", 4, "test4", "user4", "test_user4",
                "test_password4","test_city4", "test_state4", "test_zipcode4", "test_country4");
        // act
        when(userRepositoryTest.save(newUser)).thenReturn(newUser);
        UserModel resultUser = userServiceTest.saveOrUpdate(newUser);

        // assert
        assertEquals(newUser, resultUser);
    }

    @Test
    void shouldGetUserByUserId() {
        // arrange

        // act
        when(userRepositoryTest.findUserByUserId(1)).thenReturn(user1);
        when(userRepositoryTest.findUserByUserId(2)).thenReturn(user2);
        when(userRepositoryTest.findUserByUserId(3)).thenReturn(user3);
        UserModel result1 = userServiceTest.getUserUserById(1);
        UserModel result2 = userServiceTest.getUserUserById(2);
        UserModel result3 = userServiceTest.getUserUserById(3);

        // assert
        assertEquals(user1, result1);
        assertEquals(user2, result2);
        assertEquals(user3, result3);
    }

    @Test
    void shouldFindByUserName() {
        // arrange

        // act
        when(userRepositoryTest.findUserByUserName("test_user")).thenReturn(user1);
        when(userRepositoryTest.findUserByUserName("test_user2")).thenReturn(user2);
        when(userRepositoryTest.findUserByUserName("test_user3")).thenReturn(user3);
        UserModel result1 = userServiceTest.findUserByName("test_user");
        UserModel result2 = userServiceTest.findUserByName("test_user2");
        UserModel result3 = userServiceTest.findUserByName("test_user3");

        // assert
        assertEquals(user1, result1);
        assertEquals(user2, result2);
        assertEquals(user3, result3);
    }

    @Test
    void shouldUpdateUser() {
        // arrange

        // act
        user1.setCountry("Updated_Country");
        when(userRepositoryTest.save(user1)).thenReturn(user1);
        UserModel resultUser = userServiceTest.saveOrUpdate(user1);

        // assert
        assertEquals(user1, resultUser);
    }

    @Test
    void shouldFindUserMaxId() {
        // arrange
        UserModel last = userList.get(userList.size()-1);

        // act
        when(userRepositoryTest.findFirstByOrderByUserIdDesc()).thenReturn(last);
        UserModel result = userServiceTest.findUserMaxId();

        // assert
        assertEquals(last, result);
    }

    @Test
    void shouldLoadUserByUsername() {
        // arrange

        // act
        when(userRepositoryTest.findUserByUserName("test_user")).thenReturn(user1);
        when(userRepositoryTest.findUserByUserName("test_user5")).thenReturn(null);
        UserDetails result1 = userServiceTest.loadUserByUsername("test_user");
        UserDetails result2 = userServiceTest.loadUserByUsername("test_user5");

        // assert
        assertEquals(result1.getUsername(), "test_user");
        assertNull(result2);
    }
}