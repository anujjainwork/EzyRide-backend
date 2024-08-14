package com.example.ezyride.EzyRide.repositories;

import com.example.ezyride.EzyRide.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
}
