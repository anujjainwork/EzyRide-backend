package com.example.ezyride.EzyRide.entities;

import com.example.ezyride.EzyRide.entities.enums.PaymentMethod;
import com.example.ezyride.EzyRide.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOffLocation;

    private String dropOffLocationName;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

    private LocalDateTime startedTime;

    private LocalDateTime endedTime;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Rider> riders;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int totalRiders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private Double fare;

}
