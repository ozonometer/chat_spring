package com.chat.chat_spring.model;

/**
 * Requirement 2, handle and process HTTP requests.
 * Websocket send message model
 */
public class SendMessage {

    private String sendMgs;

    public SendMessage() {}

    public SendMessage(String name) {
        this.sendMgs = name;
    }

    public String getSendMgs() {
        return sendMgs;
    }

    public void setSendMgs(String sendMgs) {
        this.sendMgs = sendMgs;
    }
}
