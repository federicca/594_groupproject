package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

public class AverageLiveableArea implements Strategy{
    @Override
    public int getAverage(int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap) {
        double sumLivableArea = 0;
        int averageLivableArea = 0;
        if(zipCodeTreeMap.containsKey(zipCode)) {
            ZipCode code = zipCodeTreeMap.get(zipCode);
            for (Property property : code.getProperties()) {
                sumLivableArea += property.getLiveableArea();
            }
            averageLivableArea = (int) (sumLivableArea / code.getProperties().size());
        }else{
            System.out.println("No data available or invalid ZipCode");
        }
        return averageLivableArea;
    }

}
