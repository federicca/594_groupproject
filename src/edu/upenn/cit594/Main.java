package edu.upenn.cit594;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.processor.PopulationReader;
import edu.upenn.cit594.processor.PropertiesReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if(args.length == 5) {
            String violationsFormat = args[0];
            String violationsFile = args[1];
            String propertyValuesFile = args[2];
            String populationFile = args[3];
            String logfile = args[4];
        }else{
            System.out.println("Error in runtime arguments.");
        }
        //Call population reader
        PopulationReader pr = new PopulationReader();
        //Create TreeMap for ZipCodes
        TreeMap<Integer, ZipCode> zipCodeTreeMap;
        //Call function that populates the TreeMap and assigns the population value
        zipCodeTreeMap = pr.processPop("src/sample_files/population.txt");
        // 1. Total Population for All ZIP Codes
        TotalPopulation pop = new TotalPopulation();
        int totalPop = pop.getTotalPop(zipCodeTreeMap);
        System.out.println("1. Total Population for all Zip Codes: " + totalPop);
        // Create property reader and add properties to ZipCode TreeMap properties ArrayList
        PropertiesReader props = new PropertiesReader();
        props.processProperties("src/sample_files/properties_small.csv", zipCodeTreeMap);
        // 3. Average Market Value
        AverageMarketValue avgMarketValue = new AverageMarketValue();
        System.out.println("3. Average market value for ZipCode: " + avgMarketValue.averageMarketValue(19143,zipCodeTreeMap));
        // 4. Average Livable Area - Use Strategy Design
        Context context = new Context(new AverageLiveableArea());
        System.out.println("4. Average Livable Area: " + context.executeStrategy(19143,zipCodeTreeMap));
        // 5. Total Residential Market Value Per Capita
        TotalValuePC valuePC = new TotalValuePC();
        System.out.println("5. Total Residential Market Value Per Capita: " +  valuePC.getTotalValuePC(19143,zipCodeTreeMap));

        //Print for testing
        for (Map.Entry<Integer, ZipCode> entry : zipCodeTreeMap.entrySet()) {
            if(entry.getValue().getProperties() != null) {
                System.out.println("ZipCode: " + entry.getKey().toString() + " Population: " + entry.getValue().getPopulation() + " Number of Properties: " + entry.getValue().getProperties().size());
            }
        }



    }
}
