package com.chat.chat_spring.controller;

import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.service.HomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/threads")
public class HomeController {

    final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatThread>> getThreads() {
        List<ChatThread> threads = homeService.getAllThreads();
        return new ResponseEntity<>(threads, HttpStatus.OK);
    }
}
