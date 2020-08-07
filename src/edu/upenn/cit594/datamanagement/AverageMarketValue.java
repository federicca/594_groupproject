package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

public class AverageMarketValue {
    public int averageMarketValue (int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap){
        double sumMarketValue = 0;
        int averageMarketValue;
        ZipCode code = zipCodeTreeMap.get(zipCode);

       for (Property property : code.getProperties()){
           sumMarketValue += property.getMarketValue();
       }
       averageMarketValue = (int) (sumMarketValue / code.getProperties().size());
       return averageMarketValue;
    }

}
