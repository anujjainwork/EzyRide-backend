package com.example.ezyride.EzyRide.dtos;
import com.example.ezyride.EzyRide.handlers.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
public class LocationsDto {
    Long id;

    String name;

    @JsonSerialize(using = PointSerializer.class)
    Point locationPoint;
}
