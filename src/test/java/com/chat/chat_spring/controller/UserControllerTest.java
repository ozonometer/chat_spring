package com.chat.chat_spring.controller;

import com.chat.chat_spring.model.UserModel;
import com.chat.chat_spring.service.JwtFilterRequest;
import com.chat.chat_spring.service.PictureService;
import com.chat.chat_spring.service.UserService;
import com.chat.chat_spring.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@WithMockUser
@AutoConfigureMockMvc(addFilters = false) //todo find out how to add the filters into the test, this is probably an important part of the process...
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userServiceTest;
    @MockBean
    private PictureService pictureServiceTest;
    @MockBean
    private ModelMapper modelMapperTest;
    @MockBean
    private JwtUtils jwtUtilsTest;
    @MockBean
    private JwtFilterRequest jwtTest;

    @Test
    void shouldGetAllUsers() throws Exception {
        // arrange
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

        // act
        when(userServiceTest.getAllUsers()).thenReturn(userList);

        //assert
        mvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*._id").isNotEmpty())
                .andExpect(jsonPath("$.*.userId").isNotEmpty())
                .andExpect(jsonPath("$.*.firstName").isNotEmpty())
                .andExpect(jsonPath("$.*.lastName").isNotEmpty())
                .andExpect(jsonPath("$.*.userName").isNotEmpty())
                .andExpect(jsonPath("$.*.password").isNotEmpty())
                .andExpect(jsonPath("$.*.city").isNotEmpty())
                .andExpect(jsonPath("$.*.state").isNotEmpty())
                .andExpect(jsonPath("$.*.zip").isNotEmpty())
                .andExpect(jsonPath("$.*.country").isNotEmpty());
    }

    @Test
    void shouldAddMember() {
        //when()..thenReturn(Optional.of(user));
    }

    @Test
    void authenticateUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void testAddMember() {
    }

    @Test
    void addPhoto() {
    }

    @Test
    void getPicture() {
    }

    @Test
    void deletePicture() {
    }
}