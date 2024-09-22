package com.example.ezyride.EzyRide.dtos;

import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RideRequestDto {

    private Long id;

    private double[] pickUpLocation;

    private double[] dropOffLocation;

    private String dropOffLocationName;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private int totalMembers;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
