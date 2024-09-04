package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    Long id;
    String accessToken;
    String refreshToken;
    Set<Role> roles;
}
