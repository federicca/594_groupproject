package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

public class TotalValuePC {
    public int getTotalValuePC(int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap){
        double sumMarketValue = 0;
        int marketValuePC = 0;
        if(!zipCodeTreeMap.containsKey(zipCode)){
            return 0;
        }
        ZipCode code = zipCodeTreeMap.get(zipCode);
        for (Property property : code.getProperties()){
            sumMarketValue += property.getMarketValue();
        }
        if(sumMarketValue == 0 || code.getPopulation() == 0){
            return 0;
        }
        marketValuePC = (int) (sumMarketValue / code.getPopulation());
        return marketValuePC;
    }

}
