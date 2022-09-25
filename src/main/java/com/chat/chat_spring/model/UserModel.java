package com.chat.chat_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserModel {

    @Id
    @JsonProperty("_id")
    private String id;

    @NotNull(message = "Id cannot be empty")
    @JsonProperty("userId")
    private Integer userId;

    @NotNull(message = "firstName cannot be empty")
    @Size(min = 5, max = 20, message = "First Name must be between 5 to 20 characters long")
    @JsonProperty("firstName")
    private String firstName;

    @NotNull(message = "lastName cannot be empty")
    @Size(min = 5, max = 20, message = "last Name must be between 5 to 20 characters long")
    @JsonProperty("lastName")
    private String lastName;

    @NotNull(message = "userName cannot be empty")
    @Size(min = 5, max = 20, message = "User Name must be between 5 to 20 characters long")
    @JsonProperty("userName")
    private String userName;

    @NotNull(message = "password cannot be empty")
    @Size(min = 5, max = 20, message = "Password must be between 5 to 20 characters long")
    @JsonProperty("password")
    private String password;
}
