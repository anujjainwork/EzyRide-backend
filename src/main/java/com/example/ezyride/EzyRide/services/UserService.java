package com.example.ezyride.EzyRide.services;

import com.example.ezyride.EzyRide.dtos.SignUpDto;
import com.example.ezyride.EzyRide.dtos.UserDto;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
}
