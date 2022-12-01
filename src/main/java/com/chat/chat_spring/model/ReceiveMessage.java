package com.chat.chat_spring.model;

/**
 * Requirement 2, handle and process HTTP requests.
 * Websocket receive message model
 */
public class ReceiveMessage {

    private String content;

    public ReceiveMessage() {}

    public ReceiveMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
