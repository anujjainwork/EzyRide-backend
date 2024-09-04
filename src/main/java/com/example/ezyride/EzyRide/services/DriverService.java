package com.example.ezyride.EzyRide.services;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.dtos.RideDto;
import com.example.ezyride.EzyRide.dtos.RiderDto;
import com.example.ezyride.EzyRide.entities.Driver;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    RideDto acceptRide(Long rideId);

    RideDto rejectRide(Long rideId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId);

    RiderDto endRide(Long rideId);

    DriverDto getMyProfile(Long Id);

    List<RideDto> getAllMyRides();

    void updateDriverLocation(Point newLocation);

}
