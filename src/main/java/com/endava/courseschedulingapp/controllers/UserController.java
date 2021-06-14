package com.endava.courseschedulingapp.controllers;

import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Api(tags = "Users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve the User by Id ")
    public ResponseEntity<User> getSession(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping("/client")
    @ApiOperation(value = "This Method is Creating a Client User")
    public ResponseEntity<User> createClient(@RequestBody User user) {
        return new ResponseEntity<>(userService.createClient(user), HttpStatus.CREATED);
    }

    @PostMapping("/trainer")
    @ApiOperation(value = "Create a Trainer (by an User with Admin Role)")
    public ResponseEntity<User> createTrainer(@RequestBody User user) {
        return new ResponseEntity<>(userService.createTrainer(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting an User by Id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
