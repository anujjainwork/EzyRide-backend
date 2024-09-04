package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.*;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Ride;
import com.example.ezyride.EzyRide.entities.Rider;
import com.example.ezyride.EzyRide.entities.User;
import com.example.ezyride.EzyRide.exceptions.ResourceNotFoundException;
import com.example.ezyride.EzyRide.repositories.RiderRepository;
import com.example.ezyride.EzyRide.services.RideService;
import com.example.ezyride.EzyRide.services.RiderService;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderServiceImpl implements RiderService {

    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RiderServiceImpl(RiderRepository riderRepository, ModelMapper modelMapper) {
        this.riderRepository = riderRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Double rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    public RiderDto getAuthenticatedRiderDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RiderDto riderDto = modelMapper.map(riderRepository.findByEmail(user.getEmail()),RiderDto.class);
        riderDto.setName(user.getName(), riderDto.getUser());
        riderDto.setId(user.getId(), riderDto.getUser());
        return riderDto;
    }

    public Rider getRiderById(Long id) {
        return riderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Rider with id" + id + "not found"));
    }
}
