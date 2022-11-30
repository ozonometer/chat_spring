package com.chat.chat_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Requirement 2, handle and process HTTP requests.
 * Authentication response model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;
    String message;
    Integer userId;
}
