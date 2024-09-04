package com.example.ezyride.EzyRide.repositories;

import com.example.ezyride.EzyRide.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    Optional<Driver> findByUserId(Long userId);
}
