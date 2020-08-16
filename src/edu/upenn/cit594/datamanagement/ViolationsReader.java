package edu.upenn.cit594.datamanagement;

import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.logging.Logger;

public interface ViolationsReader {
    
    public List<ParkingViolation> getAllViolations(TreeMap<Integer, ZipCode> zipCodeTreeMap, Logger logger);
    
}
