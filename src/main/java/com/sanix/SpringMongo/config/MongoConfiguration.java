package com.sanix.SpringMongo.config;

import com.sanix.SpringMongo.repository.VehicleRepository;
import com.sanix.SpringMongo.service.MongoDBVehicleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.Mongo;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoTemplate mongo(Mongo mongo) throws Exception{
        return new MongoTemplate(mongo);
    }

    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean(){
        return new MongoClientFactoryBean();
    }

    @Bean
    public VehicleRepository vehicleRepository(MongoTemplate mongo){
        return new MongoDBVehicleRepository(mongo, "vehicles");
    }
}
