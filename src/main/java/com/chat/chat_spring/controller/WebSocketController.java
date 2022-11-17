package com.chat.chat_spring.controller;

import com.chat.chat_spring.model.ReceiveMessage;
import com.chat.chat_spring.model.SendMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/new/{theadId}")
    @SendTo("/topic/liveChat/{theadId}")
    public ReceiveMessage greeting(SendMessage message) {
        return new ReceiveMessage(message.getSendMgs());
    }
}
