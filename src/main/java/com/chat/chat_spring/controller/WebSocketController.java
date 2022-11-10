package com.chat.chat_spring.controller;

import com.chat.chat_spring.model.ReceiveMessage;
import com.chat.chat_spring.model.SendMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/new")
    @SendTo("/topic/liveChat")
    public ReceiveMessage greeting(SendMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new ReceiveMessage("Message, " + HtmlUtils.htmlEscape(message.getSendMgs()) + "!");
    }
}
