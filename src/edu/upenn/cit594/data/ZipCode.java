package edu.upenn.cit594.data;

import java.util.ArrayList;
import java.util.HashSet;

public class ZipCode {
    private int code;
    private int population;
    private HashSet<Property> properties;
    private HashSet<ParkingViolation> parkingViolations;

    public ZipCode(int code, int population){
        this.code = code;
        this.population = population;
        properties = new HashSet<>();
        parkingViolations = new HashSet<>();
    }

    public void addProperty(Property prop){
        this.properties.add(prop);
    }
    
    public void addViolation(ParkingViolation v) {
        parkingViolations.add(v);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public HashSet<Property> getProperties() {
        return properties;
    }

    public void setProperties(HashSet<Property> properties) {
        this.properties = properties;
    }

    public HashSet<ParkingViolation> getParkingViolations() {
        return parkingViolations;
    }

    public void setParkingViolations(HashSet<ParkingViolation> parkingViolations) {
        this.parkingViolations = parkingViolations;
    }
}
