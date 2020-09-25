package com.sanix.SpringMongo.repository;

import com.sanix.SpringMongo.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String > {

    Vehicle findByVehicleNo(String vehicleNo);
}
