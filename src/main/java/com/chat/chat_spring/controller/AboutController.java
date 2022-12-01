package com.chat.chat_spring.controller;

import com.chat.chat_spring.dto.AboutDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.chat.chat_spring.utils.Constants.BACK_END_VERSION;

/**
 * Requirement 2, handle and process HTTP requests.
 * Controller to handle http request from about page.
 */
@Controller
@RequestMapping("/about")
public class AboutController {

    /**
     * Requirement 2, handle and process HTTP requests.
     * Gets current version of backend
     * @return AboutDto
     */
    @GetMapping("/backend")
    public ResponseEntity<AboutDto> getThreads() {
        AboutDto about = new AboutDto();
        about.setBackendVersion(BACK_END_VERSION);
        return new ResponseEntity<>(about, HttpStatus.OK);
    }
}
