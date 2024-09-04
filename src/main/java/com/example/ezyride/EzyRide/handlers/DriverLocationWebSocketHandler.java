package com.example.ezyride.EzyRide.handlers;

import com.example.ezyride.EzyRide.services.impl.DriverServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/location")
public class DriverLocationWebSocketHandler extends TextWebSocketHandler {

    private final DriverServiceImpl driverService;
    private final GeometryFactory geometryFactory;
    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public DriverLocationWebSocketHandler(DriverServiceImpl driverService, GeometryFactory geometryFactory, ObjectMapper objectMapper) {
        this.driverService = driverService;
        this.geometryFactory = geometryFactory;
        this.objectMapper = objectMapper;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Authentication authentication = (Authentication) attributes.get("userAuthentication");

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Security context set with user: " + authentication.getName());
            sessions.put(session.getId(), session);
        } else {
            System.out.println("No authentication found in session attributes");
        }
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Authentication authentication = (Authentication) session.getAttributes().get("userAuthentication");
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Security context set with user: " + authentication.getName());
            // Parse the incoming message
            String payload = message.getPayload();
            System.out.println("Received message: " + payload);

            // Assuming the payload is a JSON string with latitude and longitude
            JsonNode node = objectMapper.readTree(payload);
            double latitude = node.get("latitude").asDouble();
            double longitude = node.get("longitude").asDouble();

            // Update the driver's location in the database
            Point newLocation = geometryFactory.createPoint(new Coordinate(latitude, longitude));
            newLocation.setSRID(4326);
            driverService.updateDriverLocation(newLocation);

            broadcastLocationUpdate(latitude,longitude);
        } else {
            System.out.println("No authentication found in session attributes");
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId()); // Remove the session from the map
        System.out.println("Session closed and removed: " + session.getId());
    }

    private void broadcastLocationUpdate(double latitude, double longitude) {
        sessions.forEach((sessionId, session) -> {
            try {
                if (session.isOpen()) {
                    String locationUpdate = objectMapper.createObjectNode()
                            .put("latitude", latitude)
                            .put("longitude", longitude)
                            .toString();
                    session.sendMessage(new TextMessage(locationUpdate));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
