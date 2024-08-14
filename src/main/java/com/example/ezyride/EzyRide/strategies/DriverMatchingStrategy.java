package com.example.ezyride.EzyRide.strategies;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.entities.Driver;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequestDto rideRequestDto);
}
