package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Rider;
import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideStatus;
import com.example.ezyride.EzyRide.handlers.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {
    private Long id;

    @JsonSerialize(using = PointSerializer.class)
    private Point pickUpLocation;
    @JsonSerialize(using = PointSerializer.class)
    private Point dropOffLocation;
    @JsonSerialize(using = PointSerializer.class)
    private Point currentLocation;

    private LocalDateTime startedTime;

    private LocalDateTime endedTime;

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;
}
