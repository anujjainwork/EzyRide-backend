package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.Rider;
import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideRequestStatus;
import com.example.ezyride.EzyRide.handlers.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@Setter
public class RideRequestDto {

    private Long id;

    private double[] pickUpLocation;

    private double[] dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
