package com.sanix.SpringMongo.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sanix.SpringMongo.models.Vehicle;
import com.sanix.SpringMongo.repository.VehicleRepository;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

public class MongoDBVehicleRepository implements VehicleRepository {

    private final Mongo mongo;//MongoDB client
    private final String collectionName;//the name of the collection
    private final String databaseName;//the name of the database

    public MongoDBVehicleRepository(Mongo mongo,
                                    String collectionName,
                                    String databaseName) {
        this.mongo = mongo;
        this.collectionName = collectionName;
        this.databaseName = databaseName;
    }

    @Override
    public long count(){
        return getCollection().count();

    }

    @Override
    public void save(Vehicle vehicle){
        BasicDBObject query=new BasicDBObject("vehicleNo", vehicle.getVehicleNo());
        //To store objects, start by transforming the domain object Vehicle into
        //a DBObject in this case(BasicDBObject)
        DBObject dbVehicle=transform(vehicle);
        DBObject fromDB =getCollection().findAndModify(query, dbVehicle);
        //try first to update, if failed create a new vehicle
        if(fromDB==null){
            getCollection().insert(dbVehicle);
        }
    }

    @Override
    public void delete(Vehicle vehicle){
        BasicDBObject query=new BasicDBObject("vehicleNo", vehicle.getVehicleNo());
        getCollection().remove(query);
    }

    @Override
    public List<Vehicle> findAll(){
        DBCursor cursor=getCollection().find(null);
        List<Vehicle> vehicles=new ArrayList<>(cursor.size());
        for(DBObject dbObject:cursor){
            vehicle.add(transform(dbObject));
        }
        return vehicles;
    }

    @Override
    public Vehicle findByVehicleNo(String vehicleNo){
        BasicDBObject query=new BasicDBObject("vehicleNo", vehicleNo);
        DBObject dbVehicle=getCollection().findOne(query);
        return transform(dbVehicle);
    }

    private DBCollection getCollection(){
        return mongo.getDB(databaseName).getCollection(collectionName);
        //the method gets a connection to the database and
        //returns the configured DBCollection
    }

    private Vehicle transform(DBObject dbVehicle){
        return new Vehicle(
                (String) dbVehicle.get("vehicleNo"),
                (String) dbVehicle.get("color"),
                (int) dbVehicle.get("wheel"),
                (int) dbVehicle.get("seat")
        );
    }

    private DBObject transform(Vehicle vehicle){
        BasicDBObject dbVehicle=new BasicDBObject("vehicleNo", vehicle.getVehicleNo())
            .append("color", vehicle.getColor())
            .append("wheel", vehicle.getWheel())
            .append("seat", vehicle.getSeat());
        return dbVehicle;
    }

    @PreDestroy
    public void cleanUp(){
        mongo.dropDatabase(databaseName);
    }
}
