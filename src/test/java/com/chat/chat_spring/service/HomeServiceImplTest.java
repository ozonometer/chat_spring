package com.chat.chat_spring.service;

import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class HomeServiceImplTest {
    @Mock
    private ChatRepository chatRepositoryTest;
    @InjectMocks
    private HomeServiceImpl homeServiceTest;

    @Test
    void shouldGetThreads() {
        // arrange
        List<ChatThread> threadList = new LinkedList<>();
        ChatThread thread1 = new ChatThread("1", 1, "test_thread_name", "01/01/2000");
        ChatThread thread2 = new ChatThread("2", 2, "test_thread_name2", "01/01/2000");
        ChatThread thread3 = new ChatThread("3", 3, "test_thread_name3", "01/01/2000");
        threadList.add(thread1);
        threadList.add(thread2);
        threadList.add(thread3);

        // act
        when(chatRepositoryTest.findAll()).thenReturn(threadList);
        List<ChatThread> resultList = homeServiceTest.getAllThreads();

        //assert
        assertEquals(threadList, resultList);
    }
}