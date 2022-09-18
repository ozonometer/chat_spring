package com.chat.chat_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "chatThread")
public class ChatThread {

    @Id
    @JsonProperty("_id")
    private String id;

    @Getter @Setter
    @JsonProperty("orderId")
    private Integer orderId;


    @Getter @Setter
    @JsonProperty("threadName")
    private String threadName;

    @Getter @Setter
    @JsonProperty("date")
    private String date;

    @Override
    public String toString() {
        return "ChatThread{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", threadName='" + threadName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
