package com.chat.chat_spring.service;

import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    final ChatRepository chatRepository;

    public HomeServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<ChatThread> getAllThreads() {
        return chatRepository.findAll();
    }

    public ChatThread findThreadMaxId() {
        return chatRepository.findFirstByOrderByThreadIdDesc();
    }

    public ChatThread findByThreadName(String threadName) {
        return chatRepository.findFirstByThreadName(threadName);
    }

    public ChatThread saveOrUpdate(ChatThread chatThread) {
        return chatRepository.save(chatThread);
    }

    @Override
    public ChatThread getOneByThreadId(Integer theadId) {
        return chatRepository.findFirstByThreadId(theadId);
    }

    @Override
    public List<ChatThread> getAllByKeyword(String keyword) {
        return chatRepository.findAllByThreadName(keyword);
    }
}
