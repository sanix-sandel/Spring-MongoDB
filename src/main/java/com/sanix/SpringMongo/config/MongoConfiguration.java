package com.sanix.SpringMongo.config;

import com.sanix.SpringMongo.repository.VehicleRepository;
import com.sanix.SpringMongo.service.MongoDBVehicleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.Mongo;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sanix.SpringMongo")
//nables detection of interfaces that
//extend the Spring Data CrudRepository and are used for domain
// objects annotated with @Document.
public class MongoConfiguration {

    public static final String DB_NAME="vehicledb";

    @Bean
    public MongoTemplate mongoTemplate(Mongo mongo) throws Exception{
        return new MongoTemplate(mongo, DB_NAME);
    }

    @Bean
    public MongoClientFactoryBean mongoFactoryBean(){
        return new MongoClientFactoryBean();
    }


}
