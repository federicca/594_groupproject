package edu.upenn.cit594.datamanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.data.Car;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.logging.Logger;

public abstract class SuperViolationsReader extends SuperReader {

    // Date format for input files
    protected static final DateFormat DF = new SimpleDateFormat("yyyy-mm-dd'T'kk:mm:ss'Z'");
    
    public SuperViolationsReader(String filename) {
        super(filename);
    }
    
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
    protected void transferData(Date timeStamp, int fineDue, String description, int carID, String carState,
            int violationID, int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap, List<ParkingViolation> violations) {
        // Create car object
        Car car = new Car(carID, carState);
        
        // Create ParkingViolation object
        ParkingViolation violation = new ParkingViolation(timeStamp, fineDue, description, carID, carState, violationID, zipCode);
        violations.add(violation);
        
        // Add this violation to the HS of violations for this car
        car.addViolation(violation);
        
        // Get ZipCode object and add violation to list of violations
        if (zipCodeTreeMap.containsKey(zipCode)) {
            ZipCode zc = zipCodeTreeMap.get(zipCode); 
            zc.addViolation(violation);
        } else { // if unknown zip code
            ZipCode unknown = zipCodeTreeMap.get(-1); 
            unknown.addViolation(violation);
        }
    }
    
}
