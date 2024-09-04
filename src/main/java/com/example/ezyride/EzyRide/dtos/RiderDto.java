package com.example.ezyride.EzyRide.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDto {
    private UserDto user;
//    private String name;
//    private Long id;
    private Double rating;
    private String emrgncycontactno;
    private String roll_no;
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

    public void setName(String name,UserDto user) {
        user.setName(name);
    }

    public void setId(Long id, UserDto user){
        user.setId(id);
    }
}
