package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.logging.Logger;

public class JsonViolationsReader extends SuperViolationsReader implements ViolationsReader {
    
    public JsonViolationsReader(String filename, Logger logger) {
        super(filename, logger);
    }

    @Override
    public List<ParkingViolation> getAllViolations(TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        List<ParkingViolation> violations = new LinkedList<>();
        
        JSONParser parser = new JSONParser();
        try {
            // open file for reading
            JSONArray data = (JSONArray)parser.parse(new FileReader(filename));
            
            // log file open
            logOpenFile();
            
            // collect data
            Iterator iter = data.iterator();
            while (iter.hasNext()) {
                JSONObject jsonViolation = (JSONObject) iter.next();
                
                // get timeStamp
                String dateString = (String) jsonViolation.get("date");
                Date timeStamp = null;

                if (!dateString.equals("")) {
                    try {
                        timeStamp = DF.parse(dateString);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }

                // get fineDue
                int fineDue = 0;
                try {
                    long fineDueLong = (long) jsonViolation.get("fine");
                    fineDue = (int) fineDueLong;
                } catch (NumberFormatException ignore) {
                    // do nothing
                }

                
                // get description
                String description = (String) jsonViolation.get("violation");
                
                // get carID
                String carIDString = (String) jsonViolation.get("plate_id");
                int carID = 0;
                try {
                    carID = Integer.parseInt(carIDString);
                } catch (NumberFormatException ignore) {
                    // do nothing
                }
                
                // get carState
                String carState = (String) jsonViolation.get("state");
                
                // get violationID
                int violationID = 0;
                try {
                    long violationIDLong = (long) jsonViolation.get("ticket_number");
                    violationID = (int) violationIDLong;
                } catch (NumberFormatException ignore) {
                    // do nothing
                }
                
                // get zipCode
                String zipString = (String) jsonViolation.get("zip_code");
                int zipCode = -1;
                if (!zipString.equals("")) {
                    zipCode = Integer.parseInt(zipString);
                }
                
                transferData(timeStamp, fineDue, description, carID, carState, violationID, zipCode, zipCodeTreeMap, violations);
            }
        } catch (IOException | ParseException e) {
            System.out.println("JSON parse unsuccessful");
            e.printStackTrace();
        }
        return violations;
    }
}
