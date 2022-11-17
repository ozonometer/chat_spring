package com.chat.chat_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatThreadDto {
    private String id;

    private Integer threadId;

    @NotNull(message = "authorId cannot be empty")
    private Integer authorId;

    @NotNull(message = "Author Name cannot be empty")
    @Size(min = 3, max = 20, message = "Author Name must be between 3 to 20 characters long")
    private String authorUserName;

    @NotNull(message = "Thread Name cannot be empty")
    @Size(min = 3, max = 50, message = "Thread Name must be between 3 to 50 characters long")
    private String threadName;

    @NotNull(message = "Thread Description cannot be empty")
    @Size(min = 3, max = 100, message = "Thread Description must be between 3 to 100 characters long")
    private String threadDescription;

    @JsonProperty("createdDate")
    private String createdDate;
}
