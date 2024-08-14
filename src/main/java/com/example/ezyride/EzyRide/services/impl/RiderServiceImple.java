package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.dtos.RideDto;
import com.example.ezyride.EzyRide.dtos.RiderDto;
import com.example.ezyride.EzyRide.services.DriverService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderServiceImple implements DriverService {
    @Override
    public RideDto acceptRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto rejectRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto endRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto getMyProfile(Long Id) {
        return null;
    }


    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public void updateDriverLocation(Long driverId, Point newLocation) {

    }
}
