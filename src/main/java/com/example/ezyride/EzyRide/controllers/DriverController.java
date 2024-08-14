package com.example.ezyride.EzyRide.controllers;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.services.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/Id")
    DriverDto getDriverById(@RequestParam Long Id){
        return driverService.getMyProfile(Id);
    }

}
