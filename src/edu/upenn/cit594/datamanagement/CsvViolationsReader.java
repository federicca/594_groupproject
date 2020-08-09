package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;

public class CsvViolationsReader implements ViolationsReader {

    @Override
    public List<ParkingViolation> getAllViolations(String filename) {
        List<ParkingViolation> violationList = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String[] data = in.nextLine().split(",");
                // going to have to work with Date object formatting
                String dateString = data[0];
                int fineDue = Integer.parseInt(data[1]);
                String description = data[2];
                int carID = Integer.parseInt(data[3]);
                String carState = data[4];
                int violationID = Integer.parseInt(data[5]);
                int zipCode = Integer.parseInt(data[6]);
                
                violationList.add(new ParkingViolation(null, fineDue, description, carID, carState, violationID, zipCode));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Parking Violations CSV file does not exist or cannot be opened for reading.");
            e.printStackTrace();
        }
        return null;
    }
}
