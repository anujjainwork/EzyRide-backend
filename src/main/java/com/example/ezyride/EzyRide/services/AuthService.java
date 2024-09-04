package com.example.ezyride.EzyRide.services;

import com.example.ezyride.EzyRide.dtos.*;
import org.apache.coyote.BadRequestException;

public interface AuthService {

    LoginResponseDto login(LoginDto loginDto);

    UserDto signUp(SignUpDto signUpDto) throws BadRequestException;

    DriverDto onboardDriver(String userId);
}
