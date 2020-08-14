package edu.upenn.cit594.data;

import java.util.Date;

public class ParkingViolation {
    
    private Date timeStamp;
    private int fineDue;
    private String description;
    private int carID;
    private String carState;
    private int violationID;
    private int zipCode;
    
    public ParkingViolation(Date timeStamp, int fineDue, String description, int carID,
            String carState, int violationID, int zipCode) {
        this.timeStamp = timeStamp;
        this.fineDue = fineDue;
        this.description = description;
        this.carID = carID;
        this.carState = carState;
        this.violationID = violationID;
        this.zipCode = zipCode;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public int getFineDue() {
        return fineDue;
    }

    public String getDescription() {
        return description;
    }
    
    public int getCarID() {
        return carID;
    }
    
    public String getCarState() {
        return carState;
    }

    public int getViolationID() {
        return violationID;
    }

    public int getZipCode() {
        return zipCode;
    }
    
}
