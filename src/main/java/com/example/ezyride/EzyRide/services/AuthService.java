package com.example.ezyride.EzyRide.services;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.dtos.LoginDto;
import com.example.ezyride.EzyRide.dtos.SignUpDto;
import com.example.ezyride.EzyRide.dtos.UserDto;
import org.apache.coyote.BadRequestException;

public interface AuthService {

    String login(LoginDto loginDto);

    UserDto signUp(SignUpDto signUpDto) throws BadRequestException;

    DriverDto onboardDriver(String userId);
}
