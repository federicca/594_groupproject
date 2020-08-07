package edu.upenn.cit594.data;

import java.util.ArrayList;

public class ZipCode {
    private int code;
    private int population;
    private ArrayList<Property> properties = new ArrayList<>();
    private ArrayList<ParkingViolation> parkingViolations = new ArrayList<>();

    public ZipCode(int code, int population,
                   ArrayList<Property> properties, ArrayList<ParkingViolation> parkingViolations){
        this.code = code;
        this.population = population;
        this.properties = properties;
        this.parkingViolations = parkingViolations;
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
