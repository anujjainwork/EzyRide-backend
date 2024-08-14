package com.example.ezyride.EzyRide.strategies.impl;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFareCalDefaultFareImpl implements RideFareCalculationStrategy {
    @Override
    public String getRideFare(RideRequestDto rideRequestDto) {
        return "";
    }
}
