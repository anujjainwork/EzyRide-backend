package com.example.ezyride.EzyRide.repositories;

import com.example.ezyride.EzyRide.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long> {
    Object findByEmail(String email);
}
