package com.sanix.SpringMongo;

import com.mongodb.client.MongoClient;
import com.sanix.SpringMongo.config.MongoConfiguration;
import com.sanix.SpringMongo.models.Vehicle;
import com.sanix.SpringMongo.repository.VehicleRepository;
import com.sanix.SpringMongo.service.MongoDBVehicleRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;

public class Main {

    public static final String DB_NAME="vehicle";

    public static void main(String [] args)throws Exception{


        ApplicationContext ctx=
                new AnnotationConfigApplicationContext(MongoConfiguration.class);

        VehicleRepository repository=ctx.getBean(VehicleRepository.class);

        System.out.println("Number of Vehicles: "+repository.count());

        repository.save(new Vehicle("BENTLEY", "RED", 4, 4));
        repository.save(new Vehicle("Rolls Royce", "BLUE", 4, 4));

        System.out.println("Number of Vehicles: "+repository.count());

        Vehicle v=repository.findByVehicleNo("Bentley");

        System.out.println(v);

        List<Vehicle> vehicleList=repository.findAll();

        System.out.println("Number of vehicles: "+vehicleList.size());
        vehicleList.forEach(System.out::println);
        System.out.println("Number of vehicles: "+repository.count());

        //cleanup  and close
        //mongo.dropDatabase(DB_NAME);
        //mongo.close();
        ((AbstractApplicationContext) ctx).close();
    }
}
