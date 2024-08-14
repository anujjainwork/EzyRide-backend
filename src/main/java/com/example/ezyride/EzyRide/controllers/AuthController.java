package com.example.ezyride.EzyRide.controllers;

import com.example.ezyride.EzyRide.dtos.LoginDto;
import com.example.ezyride.EzyRide.dtos.SignUpDto;
import com.example.ezyride.EzyRide.dtos.UserDto;
import com.example.ezyride.EzyRide.services.UserService;
import com.example.ezyride.EzyRide.services.impl.AuthServiceImpl;
import com.example.ezyride.EzyRide.services.impl.UserServiceImpl;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

   @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) throws BadRequestException {
        UserDto userDto = authService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
   }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws BadRequestException {
        String token =  authService.login(loginDto);
        return ResponseEntity.ok(token);
    }
}
