package com.example.ezyride.EzyRide.services.impl;

import com.example.ezyride.EzyRide.dtos.DriverDto;
import com.example.ezyride.EzyRide.dtos.RideDto;
import com.example.ezyride.EzyRide.dtos.RideRequestDto;
import com.example.ezyride.EzyRide.entities.Driver;
import com.example.ezyride.EzyRide.entities.Ride;
import com.example.ezyride.EzyRide.handlers.RideRequestWebSocketHandler;
import com.example.ezyride.EzyRide.repositories.RideRepository;
import com.example.ezyride.EzyRide.services.RideService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class RideServiceImpl implements RideService {

    private final ModelMapper modelMapper;
    private final Map<String, String> otpStorage = new HashMap<>();
    private final DriverServiceImpl driverService;
    private final RiderServiceImpl riderService;
    private final RideRepository rideRepository;
    private final GeometryFactory geometryFactory;
    private final RideRequestWebSocketHandler rideRequestWebSocketHandler;
    private final RideRequestService rideRequestService;




    public RideServiceImpl(ModelMapper modelMapper, DriverServiceImpl driverService, RiderServiceImpl riderService, RideRepository rideRepository, GeometryFactory geometryFactory, RideRequestWebSocketHandler rideRequestWebSocketHandler, RideRequestService rideRequestService) {
        this.modelMapper = modelMapper;
        this.driverService = driverService;
        this.riderService = riderService;
        this.rideRepository = rideRepository;
        this.geometryFactory = geometryFactory;
        this.rideRequestWebSocketHandler = rideRequestWebSocketHandler;
        this.rideRequestService = rideRequestService;
    }

    @Override
    public Ride getRideById(Long rideId) {
        return null;
    }

    @Override
    public RideDto createNewRide(RideRequestDto rideRequestDto, String driverId, Long riderId) {
        System.out.println("RideRequestDto"+rideRequestDto);
        Ride newRide = modelMapper.map(rideRequestDto,Ride.class);
        Point pickUpLocation = geometryFactory.createPoint(new Coordinate(
                rideRequestDto.getPickUpLocation()[0],
                rideRequestDto.getPickUpLocation()[1]
        ));
        Point dropOffLocation = geometryFactory.createPoint(new Coordinate(
                rideRequestDto.getDropOffLocation()[0],
                rideRequestDto.getDropOffLocation()[1]
        ));

        newRide.setPickUpLocation(pickUpLocation);
        newRide.setDropOffLocation(dropOffLocation);
        newRide.setRider(riderService.getRiderById(riderId));
        newRide.setDriver(driverService.getDriverById(Long.valueOf(driverId)));
        rideRepository.save(newRide);
        return modelMapper.map(newRide,RideDto.class);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }

    public String generateOtp(DriverDto driverDto) {

        String otp = String.format("%04d", new Random().nextInt(10000)); // Generate a 4-digit OTP
        otpStorage.put(String.valueOf(driverDto.getUser().getId()), otp); // Store OTP associated with the driver's ID
        return otp;
    }

    public String verifyOtp(RideRequestDto rideRequestDto, String otp, String riderId) {

        // Check if the provided OTP is associated with a driver ID
        String driverId = otpStorage.entrySet().stream()
                .filter(entry -> entry.getValue().equals(otp))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (driverId != null) {
            // Optionally, remove the OTP after successful verification
            otpStorage.remove(driverId);
            System.out.println("DriverId with verified OTP"+driverId);
            Driver driver =driverService.getDriverById(Long.valueOf(driverId));

            return driver.getEmail();
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }
    // New method to handle WebSocket message sending and future completion
    public RideDto sendRideRequestToDriver(String driverEmail, RideRequestDto rideRequestDto) {
        CompletableFuture<String> future = new CompletableFuture<>();
        rideRequestService.rideRequestFutures.put(driverEmail, future);

        rideRequestWebSocketHandler.sendRideRequestToDriver(driverEmail);
        try {
            String driverResponse = future.get(60, TimeUnit.SECONDS);
            if ("RIDE_CONFIRMED".equals(driverResponse)) {
                RideDto rideDto=
                createNewRide(rideRequestDto, driverEmail, rideRequestDto.getRider().getUser().getId());
                return rideDto;
            } else {
                System.out.println("Ride declined by driver");
            }
        } catch (TimeoutException e) {
            System.out.println("Driver did not respond in time");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rideRequestService.rideRequestFutures.remove(driverEmail);
        }
        return null;
    }


}
