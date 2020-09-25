package com.sanix.SpringMongo.config;

import com.mongodb.client.MongoClient;
import com.sanix.SpringMongo.repository.VehicleRepository;
import com.sanix.SpringMongo.service.MongoDBVehicleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class MongoConfiguration {

    public static final String DB_NAME="vehicledb";

    @Bean
    public Mongo mongo() throws UnknownHostException{
        return new MongoClient();
    }

    @Bean
    public VehicleRepository vehicleRepository(Mongo mongo){
        return new MongoDBVehicleRepository(mongo, DB_NAME, " vehicles");
    }
}
