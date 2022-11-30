package com.chat.chat_spring.service;

import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Requirement 2.1.1 Spring Boot, communicate with MongoDB
 * Home page service implementation
 */
@Service
public class HomeServiceImpl implements HomeService {

    final ChatRepository chatRepository;

    public HomeServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Gets all existing threads
     * @return List of all ChatThreads
     */
    @Override
    public List<ChatThread> getAllThreads() {
        return chatRepository.findAll();
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Gets thread with maximum id, used to increment thread id when saving new
     * @return ChatThread object
     */
    public ChatThread findThreadMaxId() {
        return chatRepository.findFirstByOrderByThreadIdDesc();
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Finds existing thread if it matches new thread name, used to prevent saving new thread if name already in use
     * @return ChatThread object
     */
    public ChatThread findByThreadName(String threadName) {
        return chatRepository.findFirstByThreadName(threadName);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Saves new thread to database.
     * @param chatThread new thread object
     * @return new ChatThread
     */
    public ChatThread saveOrUpdate(ChatThread chatThread) {
        return chatRepository.save(chatThread);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Gets existing thread by it
     * @param theadId thread id
     * @return ChatThread object
     */
    @Override
    public ChatThread getOneByThreadId(Integer theadId) {
        return chatRepository.findFirstByThreadId(theadId);
    }

    /**
     * Requirement 2.1.1 Spring Boot, communicate with MongoDB
     * Finds all thread with name that match keyword, used for searching functionality
     * @param keyword search
     * @return List of ChatThread if any match found
     */
    @Override
    public List<ChatThread> getAllByKeyword(String keyword) {
        return chatRepository.findAllByThreadName(keyword);
    }
}
