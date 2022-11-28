package com.chat.chat_spring.controller;

import com.chat.chat_spring.configuration.CORSFilter;
import com.chat.chat_spring.configuration.SecurityConfiguration;
import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.service.HomeService;
import com.chat.chat_spring.service.JwtFilterRequest;
import com.chat.chat_spring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@Import({SecurityConfiguration.class, CORSFilter.class})
@ActiveProfiles("test")
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HomeService homeServiceTest;
    @MockBean
    private UserService userServiceTest;
    @MockBean
    private JwtFilterRequest jwtTest;
    @MockBean
    private ModelMapper modelMapperTest;

    @Test
    void shouldGetAllThreads() throws Exception {
        // arrange
        List<ChatThread> threadList = new LinkedList<>();
        ChatThread thread1 = new ChatThread("1", 1, 1, "admin",
                "test_thread_name", "test_thread_description", "01/01/2000");
        ChatThread thread2 = new ChatThread("2", 2, 2, "admin2",
                "test_thread_name2", "test_thread_description2", "01/01/2000");
        ChatThread thread3 = new ChatThread("3", 3, 3, "admin3",
                "test_thread_name3", "test_thread_description3", "01/01/2000");
        threadList.add(thread1);
        threadList.add(thread2);
        threadList.add(thread3);

        // act
        when(homeServiceTest.getAllThreads()).thenReturn(threadList);

        //assert
        mockMvc.perform(get("/threads/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*._id").isNotEmpty())
                .andExpect(jsonPath("$.*.threadId").isNotEmpty())
                .andExpect(jsonPath("$.*.authorId").isNotEmpty())
                .andExpect(jsonPath("$.*.authorUserName").isNotEmpty())
                .andExpect(jsonPath("$.*.threadName").isNotEmpty())
                .andExpect(jsonPath("$.*.threadDescription").isNotEmpty())
                .andExpect(jsonPath("$.*.createdDate").isNotEmpty())
                .andDo(print());
    }

    @Test
    void shouldCreateThread() throws Exception {
        // arrange
        ChatThread thread1 = new ChatThread("1", 1, 1, "admin",
                "test_thread_name", "test_thread_description", "01/01/2000");

        // act
        given(modelMapperTest.map(any(), any())).willReturn(thread1);
        given(homeServiceTest.findThreadMaxId()).willReturn(thread1);
        given(homeServiceTest.saveOrUpdate(any())).willReturn(thread1);

        // assert
        mockMvc.perform(post("/threads/createThread")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(thread1))
                .header("Access-Control-Allow-Methods", "POST")
                .header("Origin", "*"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldGetThread() throws Exception {
        // arrange
        ChatThread thread1 = new ChatThread("1", 1, 1, "admin",
                "test_thread_name", "test_thread_description", "01/01/2000");

        // act
        when(homeServiceTest.getOneByThreadId(any())).thenReturn(thread1);

        // assert
        mockMvc.perform(get("/threads/getThead/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._id").isNotEmpty())
                .andExpect(jsonPath("$.threadId").isNotEmpty())
                .andExpect(jsonPath("$.authorId").isNotEmpty())
                .andExpect(jsonPath("$.authorUserName").isNotEmpty())
                .andExpect(jsonPath("$.threadName").isNotEmpty())
                .andExpect(jsonPath("$.threadDescription").isNotEmpty())
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andDo(print());
    }
}