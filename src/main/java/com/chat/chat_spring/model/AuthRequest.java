package com.chat.chat_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Requirement 2, handle and process HTTP requests.
 * Authentication request model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotNull(message = "User Name cannot be empty!")
    @Size(min = 5, max = 20, message = "User Name must be between 5 to 20 characters long")
    private String userName;

    @NotNull(message = "Password cannot be empty!")
    @Size(min = 5, max = 20, message = "Password must be between 5 to 20 characters long")
    private String password;
}
