package com.example.ezyride.EzyRide.services.impl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class RideRequestService {
    public final Map<String, CompletableFuture<String>> rideRequestFutures = new HashMap<>();
    // Method for WebSocket handler to complete the future when driver responds
    public void completeFuture(String driverUsername, String response) {
        CompletableFuture<String> future = rideRequestFutures.get(driverUsername);
        if (future != null) {
            future.complete(response);
        }
    }
}
