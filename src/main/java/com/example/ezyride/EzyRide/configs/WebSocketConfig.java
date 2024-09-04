package com.example.ezyride.EzyRide.configs;

import com.example.ezyride.EzyRide.handlers.DriverLocationWebSocketHandler;
import com.example.ezyride.EzyRide.handlers.RideRequestWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final DriverLocationWebSocketHandler driverLocationWebSocketHandler;
    private final RideRequestWebSocketHandler rideRequestWebSocketHandler;
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Autowired
    public WebSocketConfig(DriverLocationWebSocketHandler driverLocationWebSocketHandler, RideRequestWebSocketHandler rideRequestWebSocketHandler, WebSocketAuthInterceptor webSocketAuthInterceptor) {
        this.driverLocationWebSocketHandler = driverLocationWebSocketHandler;
        this.rideRequestWebSocketHandler = rideRequestWebSocketHandler;
        this.webSocketAuthInterceptor = webSocketAuthInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(driverLocationWebSocketHandler, "/location")
                .addHandler(rideRequestWebSocketHandler,"ride-request")
                .setAllowedOrigins("*")
                .addInterceptors(webSocketAuthInterceptor);
    }

}
