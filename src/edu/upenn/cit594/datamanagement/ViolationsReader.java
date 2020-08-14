package edu.upenn.cit594.datamanagement;

import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCode;

public interface ViolationsReader {
    
    public void readViolationsIntoZipCode(String filename, TreeMap<Integer, ZipCode> zipCodeTreeMap);
    
}
