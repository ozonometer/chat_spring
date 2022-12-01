package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.ChatThread;
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
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepoTest;

    /**
     * Test case 12
     * Requirement 1.1.2, 3.1.1, and 3.1.2
     * @throws Exception
     */
    @Test
    void shouldFindAll() {
        // arrange
        chatRepoTest.deleteAll();
        List<ChatThread> threadList = new LinkedList<>();
        threadList.add(new ChatThread("1", 1, 1, "admin", "test_thread_name", "test_thread_description", "01/01/2000"));
        threadList.add(new ChatThread("2", 2, 2, "admin2", "test_thread_name2", "test_thread_description2", "01/01/2000"));
        threadList.add(new ChatThread("3", 3, 3, "admin3", "test_thread_name3", "test_thread_description3", "01/01/2000"));
        chatRepoTest.saveAll(threadList);

        // act
        List<ChatThread> result = chatRepoTest.findAll();

        //assert
        assertEquals(threadList, result);
    }
}
