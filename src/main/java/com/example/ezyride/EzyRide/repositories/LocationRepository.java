package com.example.ezyride.EzyRide.repositories;

import com.example.ezyride.EzyRide.entities.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Locations,Long> {
}
