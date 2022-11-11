package com.chat.chat_spring.controller;

import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.service.HomeService;
import com.chat.chat_spring.service.JwtFilterRequest;
import com.chat.chat_spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(HomeController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false) //todo find out how to add the filters into the test, this is probably an important part of the process...
class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HomeService homeServiceTest;
    @MockBean
    private UserService userServiceTest;
    @MockBean
    private JwtFilterRequest jwtTest;

    @Test
    void shouldGetThreads() throws Exception {
        // arrange
        List<ChatThread> threadList = new LinkedList<>();
        ChatThread thread1 = new ChatThread("1", 1, "test_thread_name", "01/01/2000");
        ChatThread thread2 = new ChatThread("2", 2, "test_thread_name2", "01/01/2000");
        ChatThread thread3 = new ChatThread("3", 3, "test_thread_name3", "01/01/2000");
        threadList.add(thread1);
        threadList.add(thread2);
        threadList.add(thread3);

        // act
        when(homeServiceTest.getAllThreads()).thenReturn(threadList);

        //assert
        mvc.perform(get("/threads/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*._id").isNotEmpty())
                .andExpect(jsonPath("$.*.orderId").isNotEmpty())
                .andExpect(jsonPath("$.*.threadName").isNotEmpty())
                .andExpect(jsonPath("$.*.date").isNotEmpty());
    }
}