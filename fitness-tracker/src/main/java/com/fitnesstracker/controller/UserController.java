package com.fitnesstracker.controller;

import com.fitnesstracker.dto.UserDTO;
import com.fitnesstracker.model.User;
import com.fitnesstracker.service.UserService;
import com.fitnesstracker.service.UserServices;
import com.fitnesstracker.util.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody UserDTO dto) {
        log.info("Registering new user with username: {}", dto.getUsername());
        UserDTO saved = userServices.register(dto);
        log.info("User successfully registered: {}", saved.getUsername());
        return new ResponseEntity<>(new ApiResponse<>(true, "User registered", saved), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Creating new user with username: {}", userDTO.getUsername());
        User createdUser = userService.createUser(userDTO);
        log.info("User successfully created with ID: {}", createdUser.getId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        log.info("Fetching user details for username: {}", username);
        UserDTO userDTO = userService.getUserByUsername(username);
        if (userDTO != null) {
            log.info("User details found for username: {}", username);
        } else {
            log.warn("No user found with username: {}", username);
        }
        return ResponseEntity.ok(userDTO);
    }
}
