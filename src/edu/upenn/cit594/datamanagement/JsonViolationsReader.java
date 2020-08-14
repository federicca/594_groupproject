package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.Car;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;

public class JsonViolationsReader implements ViolationsReader {
    
    // Date format for input file
    private static final DateFormat DF = new SimpleDateFormat("yyyy-mm-dd'T'kk:mm:ss'Z'");

    @Override
    public void readViolationsIntoZipCode(String filename, TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray data = (JSONArray)parser.parse(new FileReader(filename));
            Iterator iter = data.iterator();
            while (iter.hasNext()) {
                JSONObject jsonViolation = (JSONObject) iter.next();
                
                // get timeStamp
                String dateString = (String) jsonViolation.get("date");
                Date timeStamp = null;
                try {
                    timeStamp = DF.parse(dateString);
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                // get fineDue
                long fineDueLong = (long) jsonViolation.get("fine");
                int fineDue = (int) fineDueLong;
                
                // get description
                String description = (String) jsonViolation.get("violation");
                
                // get carID
                String carIDString = (String) jsonViolation.get("plate_id");
                int carID = Integer.parseInt(carIDString);
                
                // get carState
                String carState = (String) jsonViolation.get("state");
                
                // get violationID
                long violationIDLong = (long) jsonViolation.get("ticket_number");
                int violationID = (int) violationIDLong;
                
                // get zipCode
                String zipString = (String) jsonViolation.get("zip_code");
                int zipCode = 0;
                if (!zipString.equals("")) {
                    zipCode = Integer.parseInt(zipString);
                }
                
                // Create car object
                Car car = new Car(carID, carState);
                
                // Create ParkingViolation object
                ParkingViolation violation = new ParkingViolation(timeStamp, fineDue, description, violationID, zipCode);
                
                // Add this violation 
            }
        } catch (IOException | ParseException e) {
            System.out.println("JSON parse unsuccessful");
            e.printStackTrace();
        }        
    }
}
