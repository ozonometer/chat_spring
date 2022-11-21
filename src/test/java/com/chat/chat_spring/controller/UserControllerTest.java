package com.chat.chat_spring.controller;

import com.chat.chat_spring.configuration.CORSFilter;
import com.chat.chat_spring.configuration.SecurityConfiguration;
import com.chat.chat_spring.model.AuthRequest;
import com.chat.chat_spring.model.Picture;
import com.chat.chat_spring.model.UserModel;
import com.chat.chat_spring.service.JwtFilterRequest;
import com.chat.chat_spring.service.PictureService;
import com.chat.chat_spring.service.UserService;
import com.chat.chat_spring.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfiguration.class, CORSFilter.class})
@ActiveProfiles("test")
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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
    @MockBean
    private AuthenticationManager authenticationManagerTest;

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
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*._id").isNotEmpty())
                .andExpect(jsonPath("$.*.userId").isNotEmpty())
                .andExpect(jsonPath("$.*.firstName").isNotEmpty())
                .andExpect(jsonPath("$.*.lastName").isNotEmpty())
                .andExpect(jsonPath("$.*.userName").isNotEmpty())
                .andExpect(jsonPath("$.*.password").isNotEmpty())
                .andExpect(jsonPath("$.*.city").isNotEmpty())
                .andExpect(jsonPath("$.*.state").isNotEmpty())
                .andExpect(jsonPath("$.*.zip").isNotEmpty())
                .andExpect(jsonPath("$.*.country").isNotEmpty())
                .andDo(print());
    }

    @Test
    void shouldAddMember() throws Exception {
        // arrange
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");

        // act
        when(userServiceTest.findByUserName(any())).thenReturn(null);
        when(modelMapperTest.map(any(), any())).thenReturn(user1);
        when(userServiceTest.findUserMaxId()).thenReturn(user1);
        when(userServiceTest.saveOrUpdate(any())).thenReturn(user1);

        // assert
        mockMvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .header("Access-Control-Allow-Methods", "POST")
                        .header("Origin", "*"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        // arrange
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");

        AuthRequest authRequestTest = new AuthRequest();
        authRequestTest.setUserName(user1.getUserName());
        authRequestTest.setPassword(user1.getPassword());

        UserDetails userDetails = new User(authRequestTest.getUserName(), authRequestTest.getPassword(), new ArrayList<>());

        Map<String, Object> claimsTest = new HashMap<>();
        Date now = new Date(System.currentTimeMillis());
        Date util = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
        String jwtToken = Jwts.builder().setClaims(claimsTest).setSubject(userDetails.getUsername()).setIssuedAt(now).setExpiration(util)
                .signWith(SignatureAlgorithm.HS256, "secret").compact();

        // act
        when(authenticationManagerTest.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(authRequestTest.getUserName(), authRequestTest.getPassword()));
        when(userServiceTest.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtUtilsTest.generateToken(any())).thenReturn(jwtToken);
        when(userServiceTest.findByUserName(any())).thenReturn(user1);

        // assert
        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .header("Access-Control-Allow-Methods", "POST")
                        .header("Origin", "*"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldGetUser() throws Exception {
        // arrange
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zipcode", "test_country");

        // act
        when(userServiceTest.getUserUserById(any())).thenReturn(user1);

        //assert
        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._id").isNotEmpty())
                .andExpect(jsonPath("$.userId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").isNotEmpty())
                .andExpect(jsonPath("$.lastName").isNotEmpty())
                .andExpect(jsonPath("$.userName").isNotEmpty())
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.city").isNotEmpty())
                .andExpect(jsonPath("$.state").isNotEmpty())
                .andExpect(jsonPath("$.zip").isNotEmpty())
                .andExpect(jsonPath("$.country").isNotEmpty())
                .andDo(print());
    }

    @Test
    void shouldUpdateMember() throws Exception {
        // arrange
        UserModel user1 = new UserModel("001", 1, "test", "user", "test_user",
                "test_password","test_city", "test_state", "test_zip", "test_country");

        // act
        when(modelMapperTest.map(any(), any())).thenReturn(user1);
        when(userServiceTest.updateUser(any())).thenReturn(user1);

        // assert
        mockMvc.perform(post("/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .header("Access-Control-Allow-Methods", "POST")
                        .header("Origin", "*"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldAddPhoto() throws Exception {
        // arrange
        MockMultipartFile pic = new MockMultipartFile("picture", new byte[123456]);

        // act

        // assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addUserPicture/{userId}", 1)
                        .file("picture", pic.getBytes())
                        .header("Access-Control-Allow-Methods", "POST")
                        .header("Origin", "*"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldGetPicture() throws Exception {
        // arrange
        Picture pic = new Picture("001", 1, "test picture", new Binary(new byte[123]));

        // act
        when(pictureServiceTest.getPictureByUserId(any())).thenReturn(pic);

        //assert
        mockMvc.perform(get("/getUserPicture/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.image").isNotEmpty())
                .andExpect(jsonPath("$.id").value("001"))
                .andDo(print());
    }

    @Test
    void shouldDeletePicture() throws Exception {
        // arrange

        // act

        // assert
        mockMvc.perform(post("/deleteUserPicture/{id}", 1)
                        .header("Access-Control-Allow-Methods", "POST")
                        .header("Origin", "*"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}