package com.sanix.SpringMongo.repository;

import com.sanix.SpringMongo.models.Vehicle;

import java.util.List;

public interface VehicleRepository {

    long Count();
    void save(Vehicle vehicle);
    void delete(Vehicle vehicle);
    List<Vehicle> findAll();
    Vehicle findByVehicle(String vehicleNo);
}
