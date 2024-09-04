package com.example.ezyride.EzyRide.handlers;

import com.example.ezyride.EzyRide.controllers.RideController;
import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.services.impl.RideRequestService;
import com.example.ezyride.EzyRide.services.impl.RideServiceImpl;
import com.example.ezyride.EzyRide.services.impl.RiderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/ride-request")

public class RideRequestWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final RideRequestService rideRequestService;

    public RideRequestWebSocketHandler(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
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

    public void sendRideRequestToDriver(String driverName) {
        System.out.println("Attempting to send RIDE_REQUESTED to driverName: " + driverName);
        System.out.println("Current sessions: " + sessions);
        WebSocketSession session = sessions.get(driverName);  // Retrieve session using driverName
        if (session != null && session.isOpen()) {
            try {
                String rideRequest = "RIDE_REQUESTED";  // This could be serialized JSON or another message format
                session.sendMessage(new TextMessage(rideRequest));
                System.out.println("RIDE_REQUESTED sent to driverName: " + driverName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No active session found for driverName: " + driverName);
        }
    }
}