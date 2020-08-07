package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

/*
Your program should then display (to the console) the total market value per capita for
that ZIP Code, i.e. the total market value for all residences in the ZIP Code divided by
the population of that ZIP Code, as provided in the population input file.
The residential market value per capita must be displayed as a truncated integer, and
your program should display 0 if the total residential market value for the ZIP Code is 0,
if the population of the ZIP Code is 0, or if the user enters a ZIP Code that is not listed in
the population input file.
 */
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
