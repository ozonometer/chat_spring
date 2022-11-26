package com.chat.chat_spring.controller;

import com.chat.chat_spring.dto.ChatThreadDto;
import com.chat.chat_spring.model.ChatThread;
import com.chat.chat_spring.service.HomeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/threads")
public class HomeController {

    final HomeService homeService;

    final ModelMapper modelMapper;

    public HomeController(HomeService homeService, ModelMapper modelMapper) {
        this.homeService = homeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatThread>> getThreads() {
        List<ChatThread> threads = homeService.getAllThreads();
        return new ResponseEntity<>(threads, HttpStatus.OK);
    }

    @PostMapping(path = "/createThread", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ChatThread> createThread(@RequestBody @Valid ChatThreadDto chatThreadDto) {
        ChatThread existingChat = homeService.findByThreadName(chatThreadDto.getThreadName());
        if (!ObjectUtils.isEmpty(existingChat)) { // thread name already in use
            return new ResponseEntity<>(new ChatThread(), HttpStatus.NOT_ACCEPTABLE);
        }
        ChatThread chatThreadRequest = modelMapper.map(chatThreadDto, ChatThread.class);
        ChatThread foundThread = homeService.findThreadMaxId();
        chatThreadRequest.setThreadId(foundThread.getThreadId() + 1);
        ChatThread savedNewChat = homeService.saveOrUpdate(chatThreadRequest);
        return new ResponseEntity<>(savedNewChat, HttpStatus.OK);
    }

    @GetMapping("/getThead/{id}")
    public ResponseEntity<ChatThread> getThread(@PathVariable Integer id) {
        ChatThread chatThread = homeService.getOneByThreadId(id);
        return new ResponseEntity<>(chatThread, HttpStatus.OK);
    }

    @PostMapping("/find")
    public ResponseEntity<List<ChatThread>> getThreadsByKeyword(@RequestParam("keyword") String keyword) {
        List<ChatThread> chatThreads;
        if (!keyword.isEmpty()){
            chatThreads = homeService.getAllByKeyword(keyword);
        } else {
            chatThreads = homeService.getAllThreads();
        }
        return new ResponseEntity<>(chatThreads, HttpStatus.OK);
    }
}
