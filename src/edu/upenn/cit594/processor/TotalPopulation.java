package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

import java.util.Map;
import java.util.TreeMap;

public class TotalPopulation {
    public int getTotalPop (TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        int totalPop = 0;
        for (Map.Entry<Integer, ZipCode> entry : zipCodeTreeMap.entrySet()) {
            totalPop += entry.getValue().getPopulation();
        }
        return totalPop;
    }
}
