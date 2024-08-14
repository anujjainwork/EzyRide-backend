package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Rider;
import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideStatus;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public class RideDto {
    private Long id;

    private Point pickUpLocation;

    private Point dropOffLocation;

    private Point currentLocation;

    private LocalDateTime startedTime;

    private LocalDateTime endedTime;

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;
}
