package com.example.ezyride.EzyRide.controllers;

import com.example.ezyride.EzyRide.dtos.UserDto;
import com.example.ezyride.EzyRide.services.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    UserServiceImpl userService;

//    @PostMapping("/create")
//    UserDto sign(@RequestBody UserDto userDto){
//        return userService.createUser(userDto);
//    }
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
