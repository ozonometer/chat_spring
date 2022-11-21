package com.chat.chat_spring.service;

import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class HomeServiceImplTest {
    @Mock
    private ChatRepository chatRepositoryTest;
    @InjectMocks
    private HomeServiceImpl homeServiceTest;

    List<ChatThread> threadList;
    ChatThread thread1;
    ChatThread thread2;
    ChatThread thread3;
    @BeforeEach
    public void setUpDataBase(){
        threadList = new LinkedList<>();
        thread1 = new ChatThread("1", 1, 1, "admin",
                "test_thread_name", "test_thread_description", "01/01/2000");
        thread2 = new ChatThread("2", 2, 2, "admin2",
                "test_thread_name2", "test_thread_description2", "01/01/2000");
        thread3 = new ChatThread("3", 3, 3, "admin3",
                "test_thread_name3", "test_thread_description3", "01/01/2000");
        threadList.add(thread1);
        threadList.add(thread2);
        threadList.add(thread3);
    }

    @Test
    void shouldGetThreads() {
        // arrange

        // act
        when(chatRepositoryTest.findAll()).thenReturn(threadList);
        List<ChatThread> resultList = homeServiceTest.getAllThreads();

        //assert
        assertEquals(threadList, resultList);
    }

    @Test
    void shouldFindThreadMaxId() {
        // arrange

        // act
        when(chatRepositoryTest.findFirstByOrderByThreadIdDesc()).thenReturn(thread1);
        ChatThread result = homeServiceTest.findThreadMaxId();

        //assert
        assertEquals(result, thread1);
    }

    @Test
    void shouldFindByThreadName() {
        // arrange

        // act
        when(chatRepositoryTest.findFirstByThreadName(any())).thenReturn(thread1);
        ChatThread result = homeServiceTest.findByThreadName("test_thread_name");

        //assert
        assertEquals(result, thread1);
    }

    @Test
    void shouldSaveOrUpdateThread() {
        // arrange

        // act
        when(chatRepositoryTest.save(any())).thenReturn(thread1);
        ChatThread result = homeServiceTest.saveOrUpdate(thread1);

        //assert
        assertEquals(result, thread1);
    }

    @Test
    void shouldGetOneByThreadId() {
        // arrange

        // act
        when(chatRepositoryTest.findFirstByThreadId(any())).thenReturn(thread1);
        ChatThread result = homeServiceTest.getOneByThreadId(1);

        //assert
        assertEquals(result, thread1);
    }
}