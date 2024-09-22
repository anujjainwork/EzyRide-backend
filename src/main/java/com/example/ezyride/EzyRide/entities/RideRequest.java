package com.example.ezyride.EzyRide.entities;

import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOffLocation;

    private String dropOffLocationName;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int totalRiders;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;
}
