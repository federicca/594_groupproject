package edu.upenn.cit594.datamanagement;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.data.Car;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;

public abstract class ParentViolationsReader {
    
    /**
     * Performs the common operation of creating the data objects and adding them to the list and treemap
     * @param timeStamp
     * @param fineDue
     * @param description
     * @param carID
     * @param carState
     * @param violationID
     * @param zipCode
     * @param zipCodeTreeMap
     * @param violations
     */
    public void transferData(Date timeStamp, int fineDue, String description, int carID, String carState,
            int violationID, int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap, List<ParkingViolation> violations) {
        // Create car object
        Car car = new Car(carID, carState);
        
        // Create ParkingViolation object
        ParkingViolation violation = new ParkingViolation(timeStamp, fineDue, description, carID, carState, violationID, zipCode);
        violations.add(violation);
        
        // Add this violation to the LL of violations for this car
        car.addViolation(violation);
        
        // Get ZipCode object and add violation to list of violations
        if (zipCodeTreeMap.containsKey(zipCode)) {
            zipCodeTreeMap.get(zipCode).addViolation(violation);
        } else { // if unknown zip code
            zipCodeTreeMap.get(-1).addViolation(violation);
        }
    }
}
