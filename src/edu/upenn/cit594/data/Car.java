package edu.upenn.cit594.data;

import java.util.HashSet;
import java.util.LinkedList;

public class Car {
    
    private int id;
    private String state; 
    private LinkedList<ParkingViolation> violations; 
    // ^HS to take advantage of efficiency w/ adding nodes and contains()
    
    public Car(int id, String state) {
        this.id = id;
        this.state = state;
        violations = new LinkedList<>();
    }
    
    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public LinkedList<ParkingViolation> getViolations() {
        return violations;
    }

    /**
     * Add a parking violation to this car's list of violations
     * @param pv
     */
    public void addViolation(ParkingViolation pv) {
        if (pv == null) throw new IllegalArgumentException();
        if (violations.contains(pv)) return; // don't add duplicate violations
        violations.add(pv);
    }

}
