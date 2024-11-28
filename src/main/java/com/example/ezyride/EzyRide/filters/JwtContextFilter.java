package com.example.ezyride.EzyRide.filters;

import com.example.ezyride.EzyRide.entities.User;
import com.example.ezyride.EzyRide.services.JwtService;
import com.example.ezyride.EzyRide.services.UserService;
import com.example.ezyride.EzyRide.services.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtContextFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserServiceImpl userService;

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");

        // If the token is not present or does not start with "Bearer", pass the request along the filter chain
        if (isInvalidToken(requestTokenHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token from "Bearer " prefix
        String token = extractToken(requestTokenHeader);

        try {
            // Retrieve user ID from token
            Long userId = jwtService.getUserIdFromToken(token);

            // If valid userId and no existing authentication, set the authentication in the context
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(request, userId);
            }

        } catch (Exception e) {
            // Log token validation or extraction errors (optional)
            logger.error("Token validation failed: ", e);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidToken(String tokenHeader) {
        return tokenHeader == null || !tokenHeader.startsWith(BEARER_PREFIX);
    }

    private String extractToken(String tokenHeader) {
        // Remove the "Bearer " prefix and extract the token
        return tokenHeader.split(" ")[1];
    }

    private void authenticateUser(HttpServletRequest request, Long userId) {
        Optional<User> userOpt = Optional.ofNullable(userService.getUserById(userId));
        userOpt.ifPresent(user -> {
            // Set the authentication token in SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/websocket/topic/");
    }
}
