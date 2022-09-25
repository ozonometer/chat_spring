package com.chat.chat_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatThread")
public class ChatThread {

    @Id
    @JsonProperty("_id")
    private String id;

    @JsonProperty("orderId")
    private Integer orderId;


    @JsonProperty("threadName")
    private String threadName;

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
