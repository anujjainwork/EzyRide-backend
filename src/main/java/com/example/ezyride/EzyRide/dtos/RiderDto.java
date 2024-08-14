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
    private Double rating;
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;
}
