package com.example.ezyride.EzyRide.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Rider {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinTable(name="user_id")
    private User user;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

    private Double rating;

    private String emrgncycontactno;
}
