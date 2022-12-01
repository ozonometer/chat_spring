package com.chat.chat_spring.dto;

/**
 * Requirement 2, handle and process HTTP requests.
 * Data Transfer Object for about page
 */
public class AboutDto {
    private String backendVersion;

    public String getBackendVersion() {
        return backendVersion;
    }

    public void setBackendVersion(String backendVersion) {
        this.backendVersion = backendVersion;
    }
}
