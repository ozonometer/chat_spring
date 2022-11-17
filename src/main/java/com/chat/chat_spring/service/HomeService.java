package com.chat.chat_spring.service;

import com.chat.chat_spring.model.ChatThread;

import java.util.List;

public interface HomeService {
    List<ChatThread> getAllThreads();
    ChatThread findThreadMaxId();
    ChatThread findByThreadName(String threadName);
    ChatThread saveOrUpdate(ChatThread chatThread);
    ChatThread getOneByThreadId(Integer theadId);
}
