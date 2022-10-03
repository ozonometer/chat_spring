package com.chat.chat_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModelDto {
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

    @NotNull(message = "City cannot be empty")
    @Size(min = 5, max = 20, message = "City must be between 4 to 20 characters long")
    private String city;

    @NotNull(message = "State cannot be empty")
    @Size(min = 2, max = 20, message = "State 4 to 20 characters long")
    private String state;

    @NotNull(message = "Zip cannot be empty")
    @Size(min = 4, max = 8, message = "Zip 2 to 8 characters long")
    private String zip;

    @NotNull(message = "Country cannot be empty")
    @Size(min = 2, max = 20, message = "Country 2 to 20 characters long")
    private String country;
}
