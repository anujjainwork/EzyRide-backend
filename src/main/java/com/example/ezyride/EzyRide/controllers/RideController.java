package com.example.ezyride.EzyRide.controllers;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import com.example.ezyride.EzyRide.apiresponses.StandardApiResponse;
import com.example.ezyride.EzyRide.dtos.*;
import com.example.ezyride.EzyRide.handlers.RideRequestWebSocketHandler;
import com.example.ezyride.EzyRide.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.List;

@RestController
@RequestMapping("/requestride")
public class RideController {

    private final RiderServiceImpl riderService;
    private final DriverServiceImpl driverService;
    private final RideServiceImpl rideService;
    private final LocationServiceImpl locationService;
    private final RideRequestWebSocketHandler rideRequestWebSocketHandler;

    // This map will store the futures associated with ride requests
    public  final Map<String, CompletableFuture<String>> rideRequestFutures = new ConcurrentHashMap<>();

    @Autowired
    public RideController(RiderServiceImpl riderService, DriverServiceImpl driverService, RideServiceImpl rideService, LocationServiceImpl locationService, RideRequestWebSocketHandler rideRequestWebSocketHandler) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.rideService = rideService;
        this.locationService = locationService;
        this.rideRequestWebSocketHandler = rideRequestWebSocketHandler;
    }

    public void completeFuture(String driverUsername, String response) {
        CompletableFuture<String> future = rideRequestFutures.get(driverUsername);
        if (future != null) {
            future.complete(response);
        }
    }

    @GetMapping("/getriderdata")
    ResponseEntity<StandardApiResponse<RiderDto>> getRiderData(){
        RiderDto riderDto = riderService.getAuthenticatedRiderDetails();
        if(riderDto==null){
            StandardApiResponse<RiderDto> responseNotFound = new StandardApiResponse<>(
                    0,
                    404,
                    "Rider not found",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseNotFound);
        }
        if(riderDto!=null){
            StandardApiResponse<RiderDto> response = new StandardApiResponse<>(
                    1,
                    200,
                    "Rider found",
                    riderDto
            );
            return ResponseEntity.ok(response);
        }
        StandardApiResponse<RiderDto> responseNotFound = new StandardApiResponse<>(
                0,
                404,
                "Rider not found",
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseNotFound);
    }

    @GetMapping("/getAllLocations")
    public ResponseEntity<StandardApiResponse<List<LocationsDto>>> getAllLocations() {
        List<LocationsDto> locations = locationService.getAllLocations();

        if (!locations.isEmpty()) {
            StandardApiResponse<List<LocationsDto>> response = new StandardApiResponse<>(
                    locations.size(),
                    200,
                    "locations found",
                    locations
            );
            return ResponseEntity.ok(response);
        }

        StandardApiResponse<List<LocationsDto>> responseNotFound = new StandardApiResponse<>(
                0,
                404,
                "error finding locations",
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseNotFound);
    }

    @GetMapping("/getotp")
    ResponseEntity<StandardApiResponse<String>> getOtp(){
        DriverDto driverDto = driverService.getAuthenticatedDriverDetails();
        if(driverDto != null){
            String otp = rideService.generateOtp(driverDto);
            StandardApiResponse<String> response = new StandardApiResponse<>(
                    1,
                    200,
                    "Otp fetched successfully",
                    otp
            );
            return ResponseEntity.ok(response);
        }
        StandardApiResponse<String> notFoundResponse = new StandardApiResponse<>(
                0,
                404,
                "Otp not fetched",
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<StandardApiResponse<RideDto>> verifyOtp(@RequestBody RideRequestDto rideRequestDto, @RequestParam String otp) {
        RiderDto riderDto = riderService.getAuthenticatedRiderDetails();
        if (riderDto != null) {
            String driverEmail = rideService.verifyOtp(rideRequestDto, otp, String.valueOf(riderDto.getUser().getId()));
            if (driverEmail != null) {
                // Delegate ride request to RideServiceImpl and wait for the driver's response
                RideDto rideDto = rideService.sendRideRequestToDriver(driverEmail, rideRequestDto);

                if (rideDto != null) {
                    return ResponseEntity.ok(new StandardApiResponse<>(1, 200, "OTP verified, ride confirmed.", rideDto));
                } else {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new StandardApiResponse<>(0, 200, "OTP verified, but ride was declined by the driver.", null));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StandardApiResponse<>(0, 404, "OTP verification failed", null));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new StandardApiResponse<>(0, 403, "Authentication failed: invalid token", null));
    }


}
