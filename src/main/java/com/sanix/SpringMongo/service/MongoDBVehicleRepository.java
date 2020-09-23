package com.sanix.SpringMongo.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sanix.SpringMongo.models.Vehicle;
import com.sanix.SpringMongo.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

public class MongoDBVehicleRepository implements VehicleRepository {

    private final Mongo mongo;
    private final String collectionName;
    private final String databaseName;

    public MongoDBVehicleRepository(Mongo mongo,
                                    String collectionName,
                                    String databaseName) {
        this.mongo = mongo;
        this.collectionName = collectionName;
        this.databaseName = databaseName;
    }

    @Override
    public long Count(){
        return getCollection().count();
    }

    @Override
    public void save(Vehicle vehicle){
        BasicDBObject query=new BasicDBObject("vehicleNo", vehicle.getVehicleNo());
        DBObject dbVehicle=transform(vehicle);
        DBObject fromDB =getCollection().findAndModify(query, dbVehicle);
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
}
