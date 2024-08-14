package com.example.ezyride.EzyRide.controllers;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.services.DriverService;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private DriverService driverService;

    @MessageMapping("/updateLocation")
    public void updateLocation(DriverDto driverDto) {
        Point newLocation = driverDto.getCurrentLocation();
        driverService.updateDriverLocation(driverDto.getUser().getId(), newLocation);
    }
}
