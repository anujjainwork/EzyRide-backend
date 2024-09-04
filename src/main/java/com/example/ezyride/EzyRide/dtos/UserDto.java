package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private Set<Role> roles;
}
