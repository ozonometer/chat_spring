package com.chat.chat_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Requirement 2.1.1 Spring Boot, communicate with MongoDB
 * Chat thread repository model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatThread")
public class ChatThread {

    @Id
    @JsonProperty("_id")
    private String id;

    @JsonProperty("threadId")
    private Integer threadId;

    @JsonProperty("authorId")
    private Integer authorId;

    @JsonProperty("authorUserName")
    private String authorUserName;

    @JsonProperty("threadName")
    private String threadName;

    @JsonProperty("threadDescription")
    private String threadDescription;

    @JsonProperty("createdDate")
    private String createdDate;

    @Override
    public String toString() {
        return "ChatThread{" +
                "id='" + id + '\'' +
                ", threadId=" + threadId +
                ", authorId=" + authorId +
                ", authorUserName='" + authorUserName + '\'' +
                ", threadName='" + threadName + '\'' +
                ", threadDescription='" + threadDescription + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
