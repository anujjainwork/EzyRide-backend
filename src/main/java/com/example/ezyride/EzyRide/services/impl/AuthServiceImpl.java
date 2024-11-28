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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;
    private final UserServiceImpl userService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           RiderRepository riderRepository,
                           DriverRepository driverRepository,
                           UserServiceImpl userService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.riderRepository = riderRepository;
        this.driverRepository = driverRepository;
        this.userService = userService;
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticateUser(loginDto);

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        logger.info("User logged in successfully with ID: {}", user.getId());
        return new LoginResponseDto(user.getId(), accessToken, refreshToken, user.getRoles());
    }
    private Authentication authenticateUser(LoginDto loginDto) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", loginDto.getEmail(), e);
            throw new AuthenticationCredentialsNotFoundException("Invalid credentials");
        }
    }

    @Override
    public UserDto signUp(SignUpDto signUpDto) throws BadRequestException {
        validateUserExists(signUpDto.getEmail());

        User savedUser = createUser(signUpDto);
        createRoleBasedEntities(signUpDto, savedUser);

        logger.info("User signed up successfully with ID: {}", savedUser.getId());
        return modelMapper.map(savedUser, UserDto.class);
    }

    private void validateUserExists(String email) throws BadRequestException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            logger.warn("Attempt to sign up with existing email: {}", email);
            throw new BadRequestException("User with email already exists: " + email);
        }
    }

    private User createUser(SignUpDto signUpDto) {
        User newUser = modelMapper.map(signUpDto, User.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    private void createRoleBasedEntities(SignUpDto signUpDto, User savedUser) {
        if (signUpDto.getRoles().contains(Role.RIDER)) {
            createRider(signUpDto, savedUser);
        } else if (signUpDto.getRoles().contains(Role.DRIVER)) {
            createDriver(signUpDto, savedUser);
        }
    }

    private void createRider(SignUpDto signUpDto, User savedUser) {
        Rider newRider = modelMapper.map(signUpDto, Rider.class);
        newRider.setEmrgncycontactno(String.valueOf(signUpDto.getEmergency_no()));
        newRider.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        newRider.setUser(savedUser);
        riderRepository.save(newRider);
    }

    private void createDriver(SignUpDto signUpDto, User savedUser) {
        Driver newDriver = modelMapper.map(signUpDto, Driver.class);
        newDriver.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        newDriver.setUser(savedUser);
        driverRepository.save(newDriver);
    }

    @Override
    public DriverDto onboardDriver(String userId) {
        return null;
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        if (jwtService.validateToken(refreshToken)) {
            Long userId = jwtService.getUserIdFromToken(refreshToken);
            User user = userService.getUserById(userId);

            String accessToken = jwtService.generateAccessToken(user);
            return new LoginResponseDto(user.getId(), accessToken, refreshToken, user.getRoles());
        }
        logger.error("Invalid refresh token");
        throw new AuthenticationCredentialsNotFoundException("Refresh Token not valid");
    }
}
