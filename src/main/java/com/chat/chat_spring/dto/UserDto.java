package com.chat.chat_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    private Integer userId;

    @NotNull(message = "First Name cannot be empty")
    @Size(min = 3, max = 20, message = "First Name must be between 5 to 20 characters long")
    private String firstName;

    @NotNull(message = "Last Name cannot be empty")
    @Size(min = 3, max = 20, message = "last Name must be between 5 to 20 characters long")
    private String lastName;

    @NotNull(message = "User Name cannot be empty")
    @Size(min = 5, max = 20, message = "User Name must be between 5 to 20 characters long")
    private String userName;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 5, max = 20, message = "Password must be between 5 to 20 characters long")
    private String password;
}
