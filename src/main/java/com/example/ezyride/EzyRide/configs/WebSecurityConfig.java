package com.example.ezyride.EzyRide.configs;

import com.example.ezyride.EzyRide.filters.JwtContextFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtContextFilter jwtContextFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        "/auth/**",          // Public APIs
                                        "/location/**",
                                        "/ride-request/**",
                                        "/swagger-ui/**",    // Swagger UI
                                        "/swagger-ui.html",  // Swagger legacy
                                        "/v3/api-docs/**",   // OpenAPI docs
                                        "/swagger-resources/**",
                                        "/error/**",
                                        "/webjars/**"      // Static assets for Swagger
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtContextFilter, UsernamePasswordAuthenticationFilter.class); // Ensure correct filter configuration
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
