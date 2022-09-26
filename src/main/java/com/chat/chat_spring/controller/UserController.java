package com.chat.chat_spring.controller;

import com.chat.chat_spring.dto.UserDto;
import com.chat.chat_spring.model.AuthRequest;
import com.chat.chat_spring.model.AuthResponse;
import com.chat.chat_spring.model.UserModel;
import com.chat.chat_spring.service.UserService;
import com.chat.chat_spring.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    final UserService userService;

    final AuthenticationManager authenticationManager;

    final ModelMapper modelMapper;

    final JwtUtils jwtUtils;

    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          ModelMapper modelMapper, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getProjects() {
        List<UserModel> userModels = userService.getAllUsers();
        return new ResponseEntity<>(userModels, HttpStatus.OK);
    }

    @PostMapping(path = "/createUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserModel> addMember(@RequestBody @Valid UserDto userDto) {
        UserModel existingUser = userService.findByUserName(userDto.getUserName());
        if (!ObjectUtils.isEmpty(existingUser)) { // username already in use
            return new ResponseEntity<>(new UserModel(), HttpStatus.NOT_ACCEPTABLE);
        }
        UserModel userModelRequest = modelMapper.map(userDto, UserModel.class);
        UserModel savedUserModel =  userService.saveOrUpdate(userModelRequest);
        return new ResponseEntity<>(savedUserModel, HttpStatus.OK);
    }

    @PostMapping(path = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            authResponse.setToken(null);
            authResponse.setMessage("Invalid user!");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
        UserDetails loadedUser = userService.loadUserByUsername(authRequest.getUserName());
        authResponse.setToken(jwtUtils.generateToken(loadedUser));
        authResponse.setMessage("User authenticated.");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
