package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.dtos.LoginDto;
import com.example.ezyride.EzyRide.dtos.SignUpDto;
import com.example.ezyride.EzyRide.dtos.UserDto;
import com.example.ezyride.EzyRide.entities.User;
import com.example.ezyride.EzyRide.repositories.UserRepository;
import com.example.ezyride.EzyRide.services.AuthService;
import com.example.ezyride.EzyRide.services.JwtService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateAccessToken(user);
        return token;
    }

    @Override
    public UserDto signUp(SignUpDto signUpDto) throws BadRequestException {

        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadRequestException("User with email already exists" + signUpDto.getEmail());
        }

        User savedUser = new User();
        savedUser = modelMapper.map(signUpDto,User.class);
        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        userRepository.save(savedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onboardDriver(String userId) {
        return null;
    }
}
