package com.example.ezyride.EzyRide.strategies.impl;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingNearestDriverImpl implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
