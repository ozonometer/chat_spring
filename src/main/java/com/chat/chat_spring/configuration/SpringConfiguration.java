package com.chat.chat_spring.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Requirement 2, handle and process HTTP requests.
 * Bean configuration for Model Mapper used to map incoming Data Type Objects to Repository model
 */
@Configuration
public class SpringConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
