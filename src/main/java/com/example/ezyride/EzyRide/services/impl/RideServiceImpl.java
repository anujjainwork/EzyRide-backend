package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Ride;
import com.example.ezyride.EzyRide.services.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class RideServiceImpl implements RideService {
    @Override
    public Ride getRideById(Long rideId) {
        return null;
    }

    @Override
    public void matchWithDrivers(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequestDto rideRequestDto, Driver driver) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }
}
