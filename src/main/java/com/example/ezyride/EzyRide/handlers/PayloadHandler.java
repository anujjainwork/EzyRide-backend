package com.example.ezyride.EzyRide.handlers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Component
public class PayloadHandler {

    public final Map<String, CompletableFuture<Map<String, Object>>> rideRequestFutures = new HashMap<>();
    // Method for WebSocket handler to complete the future when driver responds
    public void completeFuture(String driverUsername, Map<String, Object> response) {
        CompletableFuture<Map<String, Object>> future = rideRequestFutures.get(driverUsername);
        if (future != null) {
            future.complete(response);
        }
    }
}
