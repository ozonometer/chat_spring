package com.chat.chat_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Requirement 2, handle and process HTTP requests.
 * Data Transfer Object for user image
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private String image;
    private String id;
}
