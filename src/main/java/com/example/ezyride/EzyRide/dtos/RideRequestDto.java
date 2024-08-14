package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.Rider;
import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public class RideRequestDto {

    private Long id;

    private Point pickUpLocation;

    private Point dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
