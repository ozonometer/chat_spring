package com.chat.chat_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "picture")
public class Picture {

    @Id
    @JsonProperty("_id")
    private String id;

    @NotNull(message = "User id cannot be empty")
    @JsonProperty("userId")
    private Integer userId;

    @NotNull(message = "Image title cannot be empty")
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Image cannot be empty")
    @JsonProperty("image")
    private Binary image;
}
