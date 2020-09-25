package com.sanix.SpringMongo.repository;

import com.sanix.SpringMongo.models.Vehicle;

import java.util.List;

public interface VehicleRepository {

    long count();
    void save(Vehicle vehicle);
    void delete(Vehicle vehicle);
    List<Vehicle> findAll();
    Vehicle findByVehicleNo(String vehicleNo);
}
