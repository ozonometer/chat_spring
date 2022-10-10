package com.chat.chat_spring.controller;

import com.chat.chat_spring.dto.ImageDTO;
import com.chat.chat_spring.dto.UserDto;
import com.chat.chat_spring.dto.UserModelDto;
import com.chat.chat_spring.model.AuthRequest;
import com.chat.chat_spring.model.AuthResponse;
import com.chat.chat_spring.model.Picture;
import com.chat.chat_spring.model.UserModel;
import com.chat.chat_spring.service.PictureService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    final UserService userService;

    final AuthenticationManager authenticationManager;

    final ModelMapper modelMapper;

    final PictureService pictureService;

    final JwtUtils jwtUtils;

    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          ModelMapper modelMapper, PictureService pictureService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
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
        UserModel foundUser = userService.findUserMaxId();
        userModelRequest.setUserId(foundUser.getUserId() + 1);
        UserModel savedUserModel = userService.saveOrUpdate(userModelRequest);
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
        authResponse.setUserId(userService.findByUserName(loadedUser.getUsername()).getUserId());
        authResponse.setMessage("User authenticated.");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable("id") int id) {
        UserModel userModel = userService.getUserUserById(id);
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @PostMapping(path = "/updateUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserModel> addMember(@RequestBody @Valid UserModelDto userModelDto) {
        UserModel userModelDtoRequest = modelMapper.map(userModelDto, UserModel.class);
        UserModel updatedUser = userService.updateUser(userModelDtoRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/addUserPicture/{userId}")
    public ResponseEntity<Boolean> addPhoto(@RequestParam("picture") MultipartFile picture, @PathVariable Integer userId) {
        pictureService.addPicture("User picture for user id " + userId, picture, userId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/getUserPicture/{id}")
    public ResponseEntity<ImageDTO> getPicture(@PathVariable Integer id) {
        Picture picture = pictureService.getPictureByUserId(id);
        ImageDTO imageDTO = new ImageDTO();
        if (picture != null) {
            imageDTO.setImage(Base64.getEncoder().encodeToString(picture.getImage().getData()));
            imageDTO.setId(picture.getId());
        } else {
            imageDTO.setImage("");
        }
        return new ResponseEntity<>(imageDTO, HttpStatus.OK);
    }

    @PostMapping("/deleteUserPicture/{id}")
    public ResponseEntity<Boolean> deletePicture(@PathVariable String id) {
        pictureService.deletePicture(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
