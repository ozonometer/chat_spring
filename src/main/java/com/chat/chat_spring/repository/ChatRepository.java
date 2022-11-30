package com.chat.chat_spring.repository;

import com.chat.chat_spring.model.ChatThread;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Requirement 2.1.1 Spring Boot, communicate with MongoDB
 * Chat Thread Repository
 */
public interface ChatRepository extends MongoRepository<ChatThread, Integer> {

    List<ChatThread> findAll();
    ChatThread findFirstByOrderByThreadIdDesc();
    ChatThread findFirstByThreadName(String threadName);
    ChatThread findFirstByThreadId(Integer id);
    @Query(value = "{'threadName': {$regex : ?0, $options: 'i'}}")
    List<ChatThread> findAllByThreadName(String threadName);
}
