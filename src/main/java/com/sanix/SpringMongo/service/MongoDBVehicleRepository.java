package com.sanix.SpringMongo.service;


import com.sanix.SpringMongo.models.Vehicle;
import com.sanix.SpringMongo.repository.VehicleRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.PreDestroy;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoDBVehicleRepository implements VehicleRepository {

    private final MongoTemplate mongo;
    //As the mapping information is on the Vehicle class,
    // the collection name can be
    //removed from the MongoDBVehicleRepository

    public MongoDBVehicleRepository(MongoTemplate mongo, String collectionName) {
        this.mongo = mongo;
    }

    @Override
    public long count(){
        return mongo.count(null, Vehicle.class);
    }

    @Override
    public Vehicle save(Vehicle vehicle){
        return mongo.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle){
        mongo.remove(vehicle);
    }

    @Override
    public List<Vehicle> findAll(){
        return mongo.findAll(Vehicle.class);
    }

    @Override
    public Vehicle findByVehicleNo(String vehicleNo){
        return mongo.findOne(new Query(where("vehicleNo").is(vehicleNo)),
                Vehicle.class);
    }

    @PreDestroy
    public void cleanUp(){
        mongo.execute(db->{
            db.drop();
            return null;
        });
    }

    //implement saveAll method
}
