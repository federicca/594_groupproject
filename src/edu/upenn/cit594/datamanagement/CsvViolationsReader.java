package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.data.Car;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;

public class CsvViolationsReader implements ViolationsReader {
    
    // Date format for input file
    private static final DateFormat DF = new SimpleDateFormat("yyyy-mm-dd'T'kk:mm:ss'Z'");

    @Override
    public void readViolationsIntoZipCode(String filename, TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String[] data = in.nextLine().split(","); // splits each line into 6 or 7 fields
                
                // Field 0: dateString -> timeStamp (parse Date)
                String dateString = data[0];
                Date timeStamp = null;
                try {
                    timeStamp = DF.parse(dateString);
                } catch (ParseException e) {
                    System.out.println("unable to parse date from string for dateString = " + dateString);
                    e.printStackTrace();
                }
                
                // Field 1: fineDue (parse Integer)
                int fineDue = Integer.parseInt(data[1]);
                
                // Field 2: description
                String description = data[2];
                
                // Field 3: carID (parse Integer)
                int carID = Integer.parseInt(data[3]);
                
                // Field 4: carState
                String carState = data[4];
                
                // Field 5: violationID (parse Integer)
                int violationID = Integer.parseInt(data[5]);
                
                // Field 6 (if it exists): zipCode (parse Integer)
                int zipCode = 0;
                if (data.length == 7) {
                    zipCode = Integer.parseInt(data[6]);
                }
                // Create car object
                Car car = new Car(carID, carState);
                
                // Create ParkingViolation object
                ParkingViolation violation = new ParkingViolation(timeStamp, fineDue, description, violationID, zipCode);
                
                // Add this violation to the LL of violations for this car
                car.addViolation(violation);
                
                // Get ZipCode object and add violation to list of violations
                if (zipCodeTreeMap.containsKey(zipCode)) {
                    zipCodeTreeMap.get(zipCode).addViolation(violation);
                } else { // if unknown zip code
                    zipCodeTreeMap.get(-1).addViolation(violation);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Parking Violations CSV file does not exist or cannot be opened for reading.");
            e.printStackTrace();
        }
    }
}
