package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

public class AverageMarketValue implements Strategy{
    @Override
    public int getAverage (int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        double sumMarketValue = 0;
        int averageMarketValue = 0;
        if (zipCodeTreeMap.containsKey(zipCode)) {
            ZipCode code = zipCodeTreeMap.get(zipCode);
            for (Property property : code.getProperties()) {
                sumMarketValue += property.getMarketValue();
            }
            averageMarketValue = (int) (sumMarketValue / code.getProperties().size());

        } else {
            System.out.println("No data available or invalid ZipCode");
        }
        return averageMarketValue;
    }
}
