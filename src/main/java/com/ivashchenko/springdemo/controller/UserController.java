package com.ivashchenko.springdemo.controller;

import com.ivashchenko.springdemo.repository.User;
import com.ivashchenko.springdemo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }

//    @PutMapping(path = "/{id}")
//    public User updateUser(@PathVariable(name = "id") Long id, @RequestBody User user) {
//        return userService.updateUser(id, user);
//    }

    @PutMapping(path = "/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String name) {
        return userService.updateUser(id, email, name);
    }
}
