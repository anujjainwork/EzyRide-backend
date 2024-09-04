package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.LocationsDto;
import com.example.ezyride.EzyRide.repositories.LocationRepository;
import com.example.ezyride.EzyRide.services.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LocationsDto> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(location -> modelMapper.map(location, LocationsDto.class))
                .collect(Collectors.toList());
    }
}
