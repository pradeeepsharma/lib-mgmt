package com.learning.LibraryManagementSystem.controllers;

import com.learning.LibraryManagementSystem.exception.UserNotFoundException;
import com.learning.LibraryManagementSystem.models.User;
import com.learning.LibraryManagementSystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long userId) throws UserNotFoundException {
        logger.info("Fetching details for user with id :"+userId);
        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/user", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user){
        logger.info("Adding User "+user);
        userService.addUser(user);
        return new ResponseEntity<>(new User(), HttpStatus.CREATED);
    }
    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<String> getRidOfUser(@PathVariable(name = "id") Long userId){
        userService.removeUser(userId);
        return new ResponseEntity<>("user removed", HttpStatus.OK);
    }
}
