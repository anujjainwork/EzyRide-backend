package com.example.ezyride.EzyRide.handlers;

import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.services.impl.RideRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/ride-request")

public class RideRequestWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final RideRequestService rideRequestService;
    private final ObjectMapper objectMapper;


    public RideRequestWebSocketHandler(RideRequestService rideRequestService, ObjectMapper objectMapper) {
        this.rideRequestService = rideRequestService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Authentication authentication = (Authentication) session.getAttributes().get("userAuthentication");

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String driverEmail = authentication.getName();  // Use driverName as the key
            sessions.put(driverEmail, session);  // Store session using driverName as the key
            System.out.println("Session stored for email: " + driverEmail);
            System.out.println("Current sessions: " + sessions);
        } else {
            System.out.println("No authentication found in session attributes");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Authentication authentication = (Authentication) session.getAttributes().get("userAuthentication");
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String payload = message.getPayload();
            String driverEmail = authentication.getName();

            System.out.println("Received message from driverEmail: " + driverEmail + ", payload: " + payload);

            // Delegate future completion to RideRequestService
            rideRequestService.completeFuture(driverEmail, payload);
        } else {
            System.out.println("No authentication found in session attributes");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Authentication authentication = (Authentication) session.getAttributes().get("userAuthentication");

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String driverEmail = authentication.getName();  // Use driverName as the key
            sessions.put(driverEmail, session);  // Store session using driverName as the key
            System.out.println("Session stored for driverName: " + driverEmail);
            System.out.println("Current sessions: " + sessions);
        } else {
            System.out.println("No authentication found in session attributes");
        }
    }

    public void sendRideRequestToDriver(String driverName, RideRequestDto rideRequestDto) {
        System.out.println("Attempting to send RIDE_REQUESTED to driverName: " + driverName);
        System.out.println("Current sessions: " + sessions);
        WebSocketSession session = sessions.get(driverName);  // Retrieve session using driverName

        Map<String, Object> rideRequestMap = new HashMap<>();
        rideRequestMap.put("messageType", "RIDE_REQUESTED");  // This is the ride request type
        rideRequestMap.put("rideRequestData", rideRequestDto); // Include the RideRequestDto

        if (session != null && session.isOpen()) {
            try {
                // Serialize the map to JSON
                String rideRequestJson = objectMapper.writeValueAsString(rideRequestMap);
                session.sendMessage(new TextMessage(rideRequestJson));
                System.out.println("RIDE_REQUESTED sent to driverName: " + driverName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No active session found for driverName: " + driverName);
        }
    }
}
