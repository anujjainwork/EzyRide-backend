package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.*;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Rider;
import com.example.ezyride.EzyRide.entities.User;
import com.example.ezyride.EzyRide.entities.enums.Role;
import com.example.ezyride.EzyRide.repositories.DriverRepository;
import com.example.ezyride.EzyRide.repositories.RiderRepository;
import com.example.ezyride.EzyRide.repositories.UserRepository;
import com.example.ezyride.EzyRide.services.AuthService;
import com.example.ezyride.EzyRide.services.JwtService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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
    private final RiderRepository riderRepository;
    private final UserServiceImpl userService;
    private final DriverRepository driverRepository;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RiderRepository riderRepository, UserServiceImpl userService, DriverRepository driverRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.riderRepository = riderRepository;
        this.userService = userService;
        this.driverRepository = driverRepository;
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken,user.getRoles());
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

        if(signUpDto.getRoles().contains(Role.RIDER)){
            Rider newrider = new Rider();
            newrider = modelMapper.map(signUpDto, Rider.class);
            newrider.setEmrgncycontactno(String.valueOf(signUpDto.getEmergency_no()));
            newrider.setPassword(passwordEncoder.encode(savedUser.getPassword()));
            newrider.setUser(savedUser);
            riderRepository.save(newrider);
        } else if (signUpDto.getRoles().contains(Role.DRIVER)) {
            Driver newDriver = new Driver();
            newDriver = modelMapper.map(signUpDto,Driver.class);
            newDriver.setPassword(passwordEncoder.encode(savedUser.getPassword()));
            newDriver.setUser(savedUser);
            driverRepository.save(newDriver);
        }
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onboardDriver(String userId) {
        return null;
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        if(jwtService.validateToken(refreshToken)){
            Long userId = jwtService.getUserIdFromToken(refreshToken);
            User user = userService.getUserById(userId);

            String accessToken = jwtService.generateAccessToken(user);
            return new LoginResponseDto(user.getId(),accessToken,refreshToken,user.getRoles());
        }
        throw new AuthenticationCredentialsNotFoundException("Refresh Token not valid");
    }
}
