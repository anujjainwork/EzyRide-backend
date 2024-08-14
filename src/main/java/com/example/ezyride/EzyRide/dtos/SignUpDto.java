package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private Long emergency_no;
    private List<Role> roles;
}
