package com.chat.chat_spring.model;

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
