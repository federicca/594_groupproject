package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;

public class CsvViolationsReader extends ParentViolationsReader implements ViolationsReader {
    
    // Date format for input file
    private static final DateFormat DF = new SimpleDateFormat("yyyy-mm-dd'T'kk:mm:ss'Z'");

    @Override
    public List<ParkingViolation> readViolationsIntoZipCode(String filename, TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        List<ParkingViolation> violations = new LinkedList<>();
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
                
                transferData(timeStamp, fineDue, description, carID, carState, violationID, zipCode, zipCodeTreeMap, violations);
            }   
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Parking Violations CSV file does not exist or cannot be opened for reading.");
            e.printStackTrace();
        }
        return violations;
    }
}
