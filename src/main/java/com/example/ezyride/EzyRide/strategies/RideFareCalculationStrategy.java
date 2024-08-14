package com.example.ezyride.EzyRide.strategies;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;

public interface RideFareCalculationStrategy {

    String getRideFare(RideRequestDto rideRequestDto);
}
