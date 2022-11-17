package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.ChatThread;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatThread, Integer> {

    List<ChatThread> findAll();
    ChatThread findFirstByOrderByThreadIdDesc();
    ChatThread findFirstByThreadName(String threadName);
    ChatThread findFirstByThreadId(Integer id);
}
