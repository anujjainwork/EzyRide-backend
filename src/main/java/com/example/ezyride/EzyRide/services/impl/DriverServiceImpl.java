package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.dtos.RideDto;
import com.example.ezyride.EzyRide.dtos.RiderDto;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.repositories.DriverRepository;
import com.example.ezyride.EzyRide.services.DriverService;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Primary
@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper modelMapper) {
        this.driverRepository = driverRepository;
        this.modelMapper = modelMapper;
    }

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
        Driver driver = driverRepository.getById(Id);
        return modelMapper.map(driver,DriverDto.class);
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public void updateDriverLocation(Long driverId, Point newLocation) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setCurrentLocation(newLocation);
        driverRepository.save(driver);
    }
}
