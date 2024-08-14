package com.example.ezyride.EzyRide.services;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDrivers(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequestDto rideRequestDto, Driver driver);

    Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest);
}
