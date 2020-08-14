package edu.upenn.cit594.data;

import java.util.ArrayList;

public class ZipCode {
    private int code;
    private int population;
    private ArrayList<Property> properties;
    private ArrayList<ParkingViolation> parkingViolations;

    public ZipCode(int code, int population,
                   ArrayList<Property> properties, ArrayList<ParkingViolation> parkingViolations){
        this.code = code;
        this.population = population;
        this.properties = properties;
        this.parkingViolations = parkingViolations;
    }

    public void addProperty(Property prop){
        this.properties.add(prop);
    }
    
    public void addViolation(ParkingViolation v) {
        this.parkingViolations.add(v);
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

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public ArrayList<ParkingViolation> getParkingViolations() {
        return parkingViolations;
    }

    public void setParkingViolations(ArrayList<ParkingViolation> parkingViolations) {
        this.parkingViolations = parkingViolations;
    }
}
